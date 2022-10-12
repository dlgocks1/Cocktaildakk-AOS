package com.compose.cocktaildakk_compose.ui.onboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.compose.cocktaildakk_compose.domain.model.CocktailWeight
import com.compose.cocktaildakk_compose.domain.model.UserInfo
import com.compose.cocktaildakk_compose.domain.repository.UserInfoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnboardViewModel @Inject constructor(
  private val userInfoRepository: UserInfoRepository
) : ViewModel() {

  var nickname: String = "익명의 누군가"
  var level: Int = 10
  var sex: String = "Male"
  var age = 20
  var base = listOf<String>("상관 없음")
  var keyword = listOf<String>("상쾌한", "트로피컬", "가벼운")

  suspend fun insertUserinfo() {
    val params = UserInfo(
      age = age,
      sex = sex,
      level = level,
      keyword = keyword,
      base = base,
      nickname = nickname
    )
    viewModelScope.launch {
      userInfoRepository.insertUserInfo(
        userInfo = params
      )
    }
    viewModelScope.launch {
      userInfoRepository.insertCocktailWeight(
        cocktailWeight = CocktailWeight()
      )
    }
  }

  data class TagList(
    val text: String = "",
    var isSelected: Boolean = false
  )


}