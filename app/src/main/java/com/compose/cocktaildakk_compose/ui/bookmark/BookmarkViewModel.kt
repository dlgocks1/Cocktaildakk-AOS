package com.compose.cocktaildakk_compose.ui.bookmark

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.compose.cocktaildakk_compose.domain.model.Cocktail
import com.compose.cocktaildakk_compose.domain.repository.CocktailRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookmarkViewModel @Inject constructor(
  private val cocktailRepository: CocktailRepository
) : ViewModel() {

  private val _cocktailList = mutableStateOf(emptyList<Cocktail>())
  val cocktailList: State<List<Cocktail>> = _cocktailList
  private var recentlyDeleteCocktail: Cocktail? = null

  init {
    viewModelScope.launch {
      cocktailRepository.getCocktailAll().collect() { cocktails ->
        _cocktailList.value = cocktails.filter {
          it.isBookmark
        }
      }
    }
  }

  fun toggleBookmark(idx: Int) = viewModelScope.launch {
    _cocktailList.value.find {
      it.idx == idx
    }?.let {
      recentlyDeleteCocktail = it
      cocktailRepository.updateCocktail(it.copy(isBookmark = !it.isBookmark))
    }
  }

  fun restoreCocktail() {
    viewModelScope.launch {
      cocktailRepository.updateCocktail(
        recentlyDeleteCocktail?.copy(isBookmark = true) ?: return@launch
      )
      recentlyDeleteCocktail = null
    }
  }
}