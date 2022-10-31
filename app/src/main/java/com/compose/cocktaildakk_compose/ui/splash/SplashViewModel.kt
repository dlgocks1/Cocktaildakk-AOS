package com.compose.cocktaildakk_compose.ui.splash

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

  private suspend fun getVersion() = withContext(Dispatchers.IO) {
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
                  return@launch
                }
                repository.setCocktailVersion(it.version)
                updateAppInfo(onEnd)
                return@launch
              }
            }
          }
          onError("에러가 발생했습니다.")
        }
      }.addOnFailureListener { exception ->
        onError(exception.toString())
        throw exception
      }
  }

  /** firestore에서 칵테일리스트를 다운로드한다. */
  private suspend fun updateAppInfo(
    onEnd: () -> Unit,
  ) {
    repository.deleteAllBookmark()
    repository.deleteAllKeyword()
    downloadKeywordTagList(onEnd)
    downloadCocktailList(onEnd)
  }

  private fun downloadCocktailList(onEnd: () -> Unit) {
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
      }.addOnFailureListener { exception ->
        throw exception
      }
  }

  private fun downloadKeywordTagList(onEnd: () -> Unit) {
    firestore.collection("keywordTagList")
      .orderBy("idx")
      .get()
      .addOnSuccessListener { document ->
        document?.let {
          it.toObjects<KeywordTag>().forEach {
            viewModelScope.launch {
              repository.insertKeyword(it)
            }
          }
          onEnd()
        }
      }.addOnFailureListener { exception ->
        throw exception
      }
  }
}