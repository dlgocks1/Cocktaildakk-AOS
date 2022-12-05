package com.compose.cocktaildakk_compose.ui.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.compose.cocktaildakk_compose.BASE_KEYWORD
import com.compose.cocktaildakk_compose.SingletonObject.MAIN_REC_LIST
import com.compose.cocktaildakk_compose.di.DispatcherModule
import com.compose.cocktaildakk_compose.domain.model.Cocktail
import com.compose.cocktaildakk_compose.domain.model.UserCocktailWeight
import com.compose.cocktaildakk_compose.domain.repository.CocktailRepository
import com.compose.cocktaildakk_compose.domain.repository.UserInfoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val cocktailRepository: CocktailRepository,
    private val userInfoRepository: UserInfoRepository,
    @DispatcherModule.DefaultDispatcher private val defaultDispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _mainRecList = mutableStateOf(emptyList<Cocktail>())
    val mainRecList: State<List<Cocktail>> = _mainRecList

    private val _baseTagRecList = mutableStateOf(emptyList<Cocktail>())
    val baseTagRecList: State<List<Cocktail>> = _baseTagRecList

    private val _keywordRecList = mutableStateOf(emptyList<Cocktail>())
    val keywordRecList: State<List<Cocktail>> = _keywordRecList

    private val _randomRecList = mutableStateOf(emptyList<Cocktail>())
    val randomRecList: State<List<Cocktail>> = _randomRecList

    private val _User_cocktailWeight = mutableStateOf(UserCocktailWeight())

    val randomBaseTag = BASE_KEYWORD.shuffled().first()
    val randomKeywordTag = mutableStateOf("")

    init {
        getAllKeyword()
        viewModelScope.launch {
            userInfoRepository.getCocktailWeight().collectLatest {
                it?.let {
                    _User_cocktailWeight.value = it
                }
            }
        }
    }

    private fun getAllKeyword() = viewModelScope.launch {
        randomKeywordTag.value =
            cocktailRepository.getAllKeyword().first().shuffled().first().tagName
    }

    /** 유저의 정보에 따라 칵테일을 추천합니다. */
    fun getMainRecList() = viewModelScope.launch(defaultDispatcher) {
        val userInfo = async {
            userInfoRepository.getUserInfo().first()
        }
        val userWeight = async {
            userInfoRepository.getCocktailWeight().last()
        }
        check(userInfo.await() == null || userWeight.await() == null) {
            "유저 정보 또는 가중치가 설정되지 않은 상태입니다."
        }
        cocktailRepository.getCocktailAll().collectLatest { cocktails ->
            val scoreResult = cocktails.getScoreResult(userInfo.await()!!, userWeight.await()!!)
            _mainRecList.value = scoreResult
                .asSequence()
                .take(5)
                .toList()
                .map { cocktailScore ->
                    cocktails.findById(cocktailScore.id)
                }
            MAIN_REC_LIST.value = scoreResult
                .asSequence()
                .take(15)
                .toList()
                .map { cocktailScore ->
                    cocktails.findById(cocktailScore.id)
                }
        }
    }

    fun getBaseTagRecList() = viewModelScope.launch {
        cocktailRepository.getCocktailAll().collectLatest() { cocktails ->
            _baseTagRecList.value = cocktails.filter { it.base.contains(randomBaseTag) }.take(8)
        }
    }

    fun getBaseKeywordRecList() = viewModelScope.launch {
        cocktailRepository.queryCocktail(randomKeywordTag.value).collectLatest { cocktails ->
            _keywordRecList.value = cocktails
        }
    }

    fun getRandomRecList() = viewModelScope.launch {
        cocktailRepository.getCocktailAll().collectLatest {
            _randomRecList.value = it.asSequence().shuffled().take(5).toList()
        }
    }

}
