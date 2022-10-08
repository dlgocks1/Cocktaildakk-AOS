package com.compose.cocktaildakk_compose.ui.onboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.compose.cocktaildakk_compose.domain.model.UserInfo
import com.compose.cocktaildakk_compose.domain.repository.UserInfoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnboardViewModel @Inject constructor(
  private val userInfoRepository: UserInfoRepository
) : ViewModel() {

  fun insertUserinfo() = viewModelScope.launch {
    val params = UserInfo(
      age = age,
      sex = sex,
      level = level,
      keyword = keyword,
      base = base
    )
    userInfoRepository.insertUserInfo(userInfo = params)
  }

  data class TagList(
    val text: String = "",
    var isSelected: Boolean = false
  )

  var level: Int = 5
  var sex: String = "Male"
  var age = 20
  var keyword = listOf<String>()
  var base = listOf<String>()

}