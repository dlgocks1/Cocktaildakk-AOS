package com.compose.cocktaildakk_compose.ui.search.searchResult

import android.util.Log
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.compose.cocktaildakk_compose.SingletonObject.VISIBLE_SEARCH_STR
import com.compose.cocktaildakk_compose.domain.model.Cocktail
import com.compose.cocktaildakk_compose.domain.model.Cocktails
import com.compose.cocktaildakk_compose.domain.repository.CocktailRepository
import com.compose.cocktaildakk_compose.domain.repository.SearchRepository
import com.compose.cocktaildakk_compose.domain.repository.UserInfoRepository
import com.compose.cocktaildakk_compose.ui.utils.getScoreResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchResultViewModel @Inject constructor(
    private val cocktailRepository: CocktailRepository,
    private val userInfoRepository: UserInfoRepository
) : ViewModel() {

    val cocktailList = mutableStateListOf<Cocktail>()

    private val _sortByRecommand = mutableStateOf(false)
    val sortByRecommand: State<Boolean> = _sortByRecommand

    init {
        viewModelScope.launch {
            cocktailRepository.queryCocktail(VISIBLE_SEARCH_STR.value).collectLatest {
                cocktailList.addAll(it)
            }
        }
    }

    fun updateUserReccomandSortOption(option: Boolean) {
        _sortByRecommand.value = option
        if (option) {
            sortByRecommand()
        } else {
            getCocktails(VISIBLE_SEARCH_STR.value)
        }
    }

    private fun sortByRecommand() = viewModelScope.launch {
        val userInfo = async {
            userInfoRepository.getUserInfo().first()
        }
        val userWeight = async {
            userInfoRepository.getCocktailWeight().first()
        }
        require(userInfo.await() != null && userWeight.await() != null) {
            "${userInfo.await()}, ${userWeight.await()} 유저 정보 또는 가중치가 설정되지 않은 상태입니다."
        }
        val scoreResult =
            getScoreResult(
                cocktailList.toList(),
                userInfo.await()!!,
                userWeight.await()!!,
            ).sortedBy { -it.score }
        val temp = scoreResult
            .map { cocktailScore ->
                cocktailList.find { cocktailScore.id == it.idx }
                    ?: throw java.lang.IllegalArgumentException("해당 ID정보에 대한 칵테일이 존재하지 않습니다.")
            }
        cocktailList.clear()
        cocktailList.addAll(temp)
    }

    fun getCocktails(query: String = "") = viewModelScope.launch {
        cocktailList.clear()
        cocktailRepository.queryCocktail(query).collect {
            cocktailList.addAll(it)
            if (sortByRecommand.value) {
                sortByRecommand()
            }
        }
    }
}
