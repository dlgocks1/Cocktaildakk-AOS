package com.compose.cocktaildakk_compose.ui.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.compose.cocktaildakk_compose.BASE_LIST
import com.compose.cocktaildakk_compose.KEYWORD_LIST
import com.compose.cocktaildakk_compose.SingletonObject.MAIN_REC_LIST
import com.compose.cocktaildakk_compose.domain.model.Cocktail
import com.compose.cocktaildakk_compose.domain.repository.CocktailRepository
import com.compose.cocktaildakk_compose.domain.repository.UserInfoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.abs
import org.threeten.bp.LocalDate;

@HiltViewModel
class HomeViewModel @Inject constructor(
  private val cocktailRepository: CocktailRepository,
  private val userInfoRepository: UserInfoRepository
) : ViewModel() {

  private val _mainRecList = mutableStateOf(emptyList<Cocktail>())
  val mainRecList: State<List<Cocktail>> = _mainRecList

  private val _baseTagRecList = mutableStateOf(emptyList<Cocktail>())
  val baseTagRecList: State<List<Cocktail>> = _baseTagRecList

  private val _keywordRecList = mutableStateOf(emptyList<Cocktail>())
  val keywordRecList: State<List<Cocktail>> = _keywordRecList

  private val _randomRecList = mutableStateOf(emptyList<Cocktail>())
  val randomRecList: State<List<Cocktail>> = _randomRecList

  val randomBaseTag = BASE_LIST.shuffled().first()
  val randomKeywordTag = KEYWORD_LIST.shuffled().first()

  //  val onlyDate = LocalDate.now() // 2019-03-21
  fun getMainRecList() = viewModelScope.launch(Dispatchers.Default) {
    val userInfo = userInfoRepository.getUserInfo().first()
    val scoreResult = mutableListOf<Pair<Float, Int>>()
    cocktailRepository.getCocktailAll().collectLatest { cocktail ->
      if (userInfo != null) {
        cocktail.forEach {
          var score = 0f
          // 키워드 중복
          var difference = it.keyword.split(',').toSet().minus(userInfo.keyword.toSet())
          var duplicationCount = it.keyword.split(',').size - difference.size
          score += duplicationCount * 0.5f

          // 기주 중복
          difference = it.base.split(',').toSet().minus(userInfo.base.toSet())
          duplicationCount = it.base.split(',').size - difference.size
          score += duplicationCount * 1.3f

          // 알코올 레밸 체크
          score += abs(it.level - userInfo.level) * 0.3f
          scoreResult += score to it.idx
        }
      }
      scoreResult.sortBy { -it.first }
      _mainRecList.value = scoreResult.take(5).mapNotNull { pair ->
        cocktail.find { pair.second == it.idx }
      }
      MAIN_REC_LIST.value = scoreResult.take(15).mapNotNull { pair ->
        cocktail.find { pair.second == it.idx }
      }
    }
  }

  fun getBaseTagRecList() = viewModelScope.launch {
    cocktailRepository.getCocktailAll().collectLatest() { cocktails ->
      _baseTagRecList.value = cocktails.filter { it.base.contains(randomBaseTag) }.take(8)
    }
  }

  fun getBaseKeywordRecList() = viewModelScope.launch {
    cocktailRepository.queryCocktail(randomKeywordTag).collectLatest { cocktails ->
      _keywordRecList.value = cocktails
    }
  }

  fun getRandomRecList() = viewModelScope.launch {
    cocktailRepository.getCocktailAll().collectLatest {
      _randomRecList.value = it.shuffled().take(5)
    }
  }

}
