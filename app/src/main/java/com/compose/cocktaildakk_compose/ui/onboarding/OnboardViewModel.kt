package com.compose.cocktaildakk_compose.ui.onboarding

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.compose.cocktaildakk_compose.*
import com.compose.cocktaildakk_compose.domain.model.UserCocktailWeight
import com.compose.cocktaildakk_compose.domain.model.KeywordTag
import com.compose.cocktaildakk_compose.domain.model.UserInfo
import com.compose.cocktaildakk_compose.domain.repository.CocktailRepository
import com.compose.cocktaildakk_compose.domain.repository.UserInfoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnboardViewModel @Inject constructor(
    private val userInfoRepository: UserInfoRepository,
    private val cocktailRepository: CocktailRepository
) : ViewModel() {

    var nickname: String = DEFAULT_NICKNAME
    var level: Int = DEFAULT_LEVEL
    var sex: String = DEFAULT_SEX
    var age = DEFAULT_AGE
    var base = DEFAULT_BASE
    var keyword = DEFAULT_KEYWORD

    private val _keywordTagList = mutableStateOf(emptyList<KeywordTag>())
    val keywordTagList: State<List<KeywordTag>>
        get() = _keywordTagList

    init {
        viewModelScope.launch {
            cocktailRepository.getAllKeyword().collectLatest {
                _keywordTagList.value = it
            }
        }
    }

    data class TagList(
        val text: String = "",
        var isSelected: Boolean = false
    )

    fun insertUserinfo() {
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
                userCocktailWeight = UserCocktailWeight()
            )
        }
    }


}