package com.compose.cocktaildakk_compose.ui.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.compose.cocktaildakk_compose.BASE_KEYWORD
import com.compose.cocktaildakk_compose.SingletonObject.MAIN_REC_LIST
import com.compose.cocktaildakk_compose.di.DispatcherModule
import com.compose.cocktaildakk_compose.domain.model.Cocktail
import com.compose.cocktaildakk_compose.domain.model.Cocktails
import com.compose.cocktaildakk_compose.domain.model.UserCocktailWeight
import com.compose.cocktaildakk_compose.domain.repository.CocktailRepository
import com.compose.cocktaildakk_compose.domain.repository.UserInfoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val cocktailRepository: CocktailRepository,
    private val userInfoRepository: UserInfoRepository,
    @DispatcherModule.DefaultDispatcher private val defaultDispatcher: CoroutineDispatcher,
) : ViewModel() {

    private val _mainRecCocktails = mutableStateOf(emptyList<Cocktail>())
    val mainRecCocktails: State<List<Cocktail>> = _mainRecCocktails

    private val _baseTagRecCocktails = mutableStateOf(emptyList<Cocktail>())
    val baseTagRecCocktails: State<List<Cocktail>> = _baseTagRecCocktails

    private val _keywordRecCocktails = mutableStateOf(emptyList<Cocktail>())
    val keywordRecCocktails: State<List<Cocktail>> = _keywordRecCocktails

    private val _dailyRandomRecCocktails = mutableStateOf(emptyList<Cocktail>())
    val dailyRandomRecCocktails: State<List<Cocktail>> = _dailyRandomRecCocktails

    private val _userCocktailWeight = mutableStateOf(UserCocktailWeight())

    val randomBaseTag = BASE_KEYWORD.shuffled().first()

    private val _randomKeywordTag = mutableStateOf("")
    val randomKeywordTag: State<String> get() = _randomKeywordTag

    init {
        viewModelScope.launch {
            initMainRec()
            userInfoRepository.getCocktailWeight().collectLatest {
                it?.let {
                    _userCocktailWeight.value = it
                }
            }
            getRandomKeyword()
            getBaseKeywordRecCocktails()
        }
    }

    fun initMainRec() = viewModelScope.launch {
        cocktailRepository.getCocktailAll().collectLatest {
            with(Cocktails(it)) {
                getMainRecCocktails(this)
                getBaseTagRecCocktails(this)
                getRandomRecCocktails(this)
            }
        }
    }

    private fun getRandomKeyword() = viewModelScope.launch {
        _randomKeywordTag.value =
            cocktailRepository.getAllKeyword().first().shuffled().first().tagName
    }

    /** 유저의 정보에 따라 칵테일을 추천합니다. */
    private fun getMainRecCocktails(cocktails: Cocktails) =
        viewModelScope.launch(defaultDispatcher) {
            val userInfo = async {
                userInfoRepository.getUserInfo().first()
            }
            val userWeight = async {
                userInfoRepository.getCocktailWeight().first()
            }
            require(userInfo.await() != null && userWeight.await() != null) {
                "${userInfo.await()}, ${userWeight.await()} 유저 정보 또는 가중치가 설정되지 않은 상태입니다."
            }

            val cocktailsByScore =
                cocktails.getScoreResult(
                    userInfo.await()!!,
                    userWeight.await()!!,
                ).sortedBy { -it.score }

            cocktailsByScore
                .take(10)
                .map { cocktailScore ->
                    cocktails.findById(cocktailScore.id)
                }.also {
                    MAIN_REC_LIST.value = it
                    _mainRecCocktails.value = it
                }
        }

    private fun getBaseTagRecCocktails(cocktails: Cocktails) = viewModelScope.launch {
        _baseTagRecCocktails.value =
            cocktails.filter { it.base.contains(randomBaseTag) }.take(8)
    }

    private fun getRandomRecCocktails(cocktails: Cocktails) = viewModelScope.launch {
        _dailyRandomRecCocktails.value = cocktails.asSequence().shuffled().take(5).toList()
    }

    fun getBaseKeywordRecCocktails() = viewModelScope.launch {
        cocktailRepository.queryCocktail(randomKeywordTag.value).collectLatest { cocktails ->
            _keywordRecCocktails.value = cocktails
        }
    }
}
