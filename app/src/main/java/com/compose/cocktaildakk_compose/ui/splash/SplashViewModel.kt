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
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val repository: CocktailRepository,
    private val userInfoDao: UserInfoDao,
    private val networkChecker: NetworkChecker,
) : ViewModel() {

    var isUserInfo: UserInfo? = null
    private val _networkState = MutableSharedFlow<NetworkState>(replay = 1)
    val networkState: SharedFlow<NetworkState> = _networkState

    init {
        getUserInfo()
        handleNetworkState()
    }

    private fun handleNetworkState() = viewModelScope.launch {
        networkChecker.networkState.collectLatest {
            _networkState.emit(it)
        }
    }

    private fun getUserInfo() = viewModelScope.launch {
        userInfoDao.getUserInfo().collect {
            it?.let {
                isUserInfo = it
            }
        }
    }

    private suspend fun getVersion(): Float {
        return repository.getCocktailVersion().first()
    }

    fun checkCocktailVersion(
        onSuccess: () -> Unit = {},
        onError: (String) -> Unit = {},
    ) {
        firestore.collection("metaData")
            .get()
            .addOnSuccessListener { query ->
                query?.let {
                    if (it.toObjects<CocktailVersion>().isNotEmpty()) {
                        it.toObjects<CocktailVersion>().first().let {
                            viewModelScope.launch {
                                val userVersion = async {
                                    getVersion()
                                }
                                if (it.version == userVersion.await()) {
                                    onSuccess()
                                } else {
                                    repository.setCocktailVersion(it.version)
                                    updateAppInfo(onSuccess)
                                }
                            }
                        }
                    }
                }
            }.addOnFailureListener { exception ->
                onError(exception.toString())
                exception.printStackTrace()
                throw exception
            }
    }

    /** firestore에서 칵테일리스트를 다운로드한다. */
    private suspend fun updateAppInfo(
        onSuccess: () -> Unit,
    ) {
        repository.deleteAllBookmark()
        repository.deleteAllKeyword()
        downloadKeywordTagList(onSuccess)
        downloadCocktailList(onSuccess)
    }

    private fun downloadCocktailList(onSuccess: () -> Unit) {
        firestore.collection("cocktailList")
            .orderBy("idx")
            .get()
            .addOnSuccessListener { document ->
                document?.let {
                    it.toObjects<Cocktail>().let {
                        viewModelScope.launch {
                            repository.addCocktailList(Cocktails(it))
                        }
                    }
                    onSuccess()
                }
            }.addOnFailureListener { exception ->
                exception.printStackTrace()
                throw exception
            }
    }

    private fun downloadKeywordTagList(onSuccess: () -> Unit) {
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
                    onSuccess()
                }
            }.addOnFailureListener { exception ->
                exception.printStackTrace()
                throw exception
            }
    }
}