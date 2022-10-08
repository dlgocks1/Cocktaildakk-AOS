package com.compose.cocktaildakk_compose.ui.mypage

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.compose.cocktaildakk_compose.domain.model.UserInfo
import com.compose.cocktaildakk_compose.domain.repository.UserInfoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MypageViewModel @Inject constructor(
  private val userInfoRepository: UserInfoRepository
) : ViewModel() {

  private val _userInfo = mutableStateOf(UserInfo())
  val userInfo: State<UserInfo> = _userInfo

  init {
    getUserInfo()
  }

  fun getUserInfo() = viewModelScope.launch {
    userInfoRepository.getUserInfo().collectLatest {
      it?.let {
        Log.i("Mypage", it.toString())
        _userInfo.value = it
        Log.i("Mypage", it.base.contains("브랜디").toString())
      }
    }
  }

  fun updateUserInfo(userInfo: UserInfo) = viewModelScope.launch {
    userInfoRepository.updateUserInfo(userInfo = userInfo)
  }

}