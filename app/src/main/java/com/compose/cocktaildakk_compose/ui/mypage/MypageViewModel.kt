package com.compose.cocktaildakk_compose.ui.mypage

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.compose.cocktaildakk_compose.domain.model.CocktailWeight
import com.compose.cocktaildakk_compose.domain.model.UserInfo
import com.compose.cocktaildakk_compose.domain.repository.UserInfoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MypageViewModel @Inject constructor(
  private val userInfoRepository: UserInfoRepository
) : ViewModel() {

  private val _userInfo = mutableStateOf(UserInfo())
  val userInfo: State<UserInfo> = _userInfo

  private val _cocktailWeight = mutableStateOf(CocktailWeight())
  val cocktailWeight: State<CocktailWeight> = _cocktailWeight

  init {
    getUserInfo()
    getCocktailWeight()
  }

  // 1 상관없음, 2 안 중요, 3보통, 4중요, 5 매주중요
  fun updateWeight(
    keywordWeight: Int,
    baseWeight: Int,
    levelWeight: Int
  ) = viewModelScope.launch {
    userInfoRepository.updateCocktailWeight(
      CocktailWeight(
        leveldWeight = levelWeight,
        baseWeight = baseWeight,
        keywordWeight = keywordWeight
      )
    )
  }

  private fun getUserInfo() = viewModelScope.launch {
    userInfoRepository.getUserInfo().collectLatest {
      it?.let {
        Log.i("MypageViewModel", it.toString())
        _userInfo.value = it
      }
    }
  }

  private fun getCocktailWeight() = viewModelScope.launch {
    userInfoRepository.getCocktailWeight().collectLatest {
      it?.let {
        Log.i("MypageViewModel", it.toString())
        _cocktailWeight.value = it
      }
    }
  }

  fun updateUserInfo(userInfo: UserInfo) = viewModelScope.launch {
    userInfoRepository.updateUserInfo(userInfo = userInfo)
  }

}