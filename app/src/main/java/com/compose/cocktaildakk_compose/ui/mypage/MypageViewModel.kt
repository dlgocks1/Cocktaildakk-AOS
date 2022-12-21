package com.compose.cocktaildakk_compose.ui.mypage

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.compose.cocktaildakk_compose.domain.model.UserCocktailWeight
import com.compose.cocktaildakk_compose.domain.model.KeywordTag
import com.compose.cocktaildakk_compose.domain.model.UserInfo
import com.compose.cocktaildakk_compose.domain.repository.CocktailRepository
import com.compose.cocktaildakk_compose.domain.repository.UserInfoRepository
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MypageViewModel @Inject constructor(
    private val userInfoRepository: UserInfoRepository,
    private val cocktailRepository: CocktailRepository,
    private val firestore: FirebaseFirestore
) : ViewModel() {

    private val _userInfo = mutableStateOf(UserInfo())
    val userInfo: State<UserInfo> = _userInfo

    private val _userCocktailWeight = mutableStateOf(UserCocktailWeight())
    val userCocktailWeight: State<UserCocktailWeight> = _userCocktailWeight

    private val _keywordTagList = mutableStateOf(emptyList<KeywordTag>())
    val keywordTagList: State<List<KeywordTag>>
        get() = _keywordTagList

    init {
        getUserInfo()
        getCocktailWeight()
        getKeywordAll()
    }

    private fun getKeywordAll() = viewModelScope.launch {
        viewModelScope.launch {
            cocktailRepository.getAllKeyword().collectLatest {
                _keywordTagList.value = it
            }
        }
    }

    fun updateWeight(
        keywordWeight: Int,
        baseWeight: Int,
        levelWeight: Int
    ) = viewModelScope.launch {
        /* 중요도 : 1 상관없음, 2 안 중요, 3보통, 4중요, 5 매주중요 */
        userInfoRepository.updateCocktailWeight(
            UserCocktailWeight(
                level = levelWeight,
                base = baseWeight,
                keyword = keywordWeight
            )
        )
    }

    private fun getUserInfo() = viewModelScope.launch {
        userInfoRepository.getUserInfo().collectLatest {
            it?.let {
                _userInfo.value = it
            }
        }
    }

    private fun getCocktailWeight() = viewModelScope.launch {
        userInfoRepository.getCocktailWeight().collectLatest {
            it?.let {
                _userCocktailWeight.value = it
            }
        }
    }

    fun updateUserInfo(userInfo: UserInfo, onFinished: () -> Boolean) = viewModelScope.launch {
        userInfoRepository.updateUserInfo(userInfo = userInfo) // 내부 DB 업데이트
        firestore.collection("userData")
            .document(userInfo.firebaseKey)
            .update(userInfo.toHashMap())
            .addOnSuccessListener {
                onFinished()
            }
    }

}