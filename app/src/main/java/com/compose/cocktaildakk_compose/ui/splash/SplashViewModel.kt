package com.compose.cocktaildakk_compose.ui.splash

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.compose.cocktaildakk_compose.data.data_source.UserInfoDao
import com.compose.cocktaildakk_compose.domain.model.*
import com.compose.cocktaildakk_compose.domain.repository.CocktailRepository
import com.compose.cocktaildakk_compose.ui.utils.NetworkChecker
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObjects
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
  private val firestore: FirebaseFirestore,
  private val repository: CocktailRepository,
  private val userInfoDao: UserInfoDao,
  private val networkChecker: NetworkChecker
) : ViewModel() {

  var isUserInfo: UserInfo? = null
  private val _networkState = MutableSharedFlow<NetworkState>(replay = 1)
  val networkState: SharedFlow<NetworkState> = _networkState

  init {
    viewModelScope.launch {
      userInfoDao.getUserInfo().collect() {
        it?.let {
          isUserInfo = it
        }
      }
    }
    viewModelScope.launch {
      networkChecker.networkState.collectLatest {
        _networkState.emit(it)
      }
    }
  }

  suspend fun getVersion() = withContext(Dispatchers.IO) {
    repository.getCocktailVersion().first()
  }

  fun checkCocktailVersion(
    onEnd: () -> Unit = {},
    onError: (String) -> Unit = {},
  ) {
    firestore.collection("metaData")
      .get()
      .addOnSuccessListener { query ->
        query?.let {
          if (it.toObjects<CocktailVersion>().isNotEmpty()) {
            it.toObjects<CocktailVersion>().first().let {
              viewModelScope.launch {
                if (it.version == getVersion()) {
                  onEnd()
                } else {
                  repository.setCocktailVersion(it.version)
                  donwloadFromFireStore(onEnd)
                }
              }
            }
          } else {
            onError("에러가 발생했습니다.")
          }
        }
      }
      .addOnFailureListener { exception ->
        onError(exception.toString())
        throw exception
      }
  }

  private suspend fun donwloadFromFireStore(
    onEnd: () -> Unit,
  ) {
    Log.i("SplashScreen", "DownLoad Start")
    repository.deleteAllBookmark()
    repository.deleteAllKeyword()

    firestore.collection("keywordTagList")
      .orderBy("idx")
      .get()
      .addOnSuccessListener { document ->
        document?.let {
          it.toObjects<KeywordTag>().forEach() {
            viewModelScope.launch {
              repository.insertKeyword(it)
            }
          }
        }
      }
      .addOnFailureListener { exception ->
        throw exception
      }
    downLoadCocktailList(onEnd)
  }

  private fun downLoadCocktailList(onEnd: () -> Unit) {
    firestore.collection("cocktailList")
      .orderBy("idx")
      .get()
      .addOnSuccessListener { document ->
        document?.let {
          it.toObjects<Cocktail>().forEach() {
            viewModelScope.launch {
              repository.addCocktail(it)
            }
          }
          onEnd()
        }
      }
      .addOnFailureListener { exception ->
        throw exception
      }
  }
}