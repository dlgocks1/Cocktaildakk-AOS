package com.compose.cocktaildakk_compose.ui.bookmark

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.compose.cocktaildakk_compose.domain.model.BookmarkIdx
import com.compose.cocktaildakk_compose.domain.model.Cocktail
import com.compose.cocktaildakk_compose.domain.repository.CocktailRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookmarkViewModel @Inject constructor(
  private val cocktailRepository: CocktailRepository
) : ViewModel() {

  private val _cocktailList = mutableStateOf(emptyList<Cocktail>())
  val cocktailList: State<List<Cocktail>> = _cocktailList
  private var recentlyDeleteCocktail: BookmarkIdx? = null

  val bookmarkList = mutableStateOf(emptyList<BookmarkIdx>())

  init {
    viewModelScope.launch {
      cocktailRepository.getAllBookmark().collectLatest {
        bookmarkList.value = it
      }
    }
    viewModelScope.launch {
      cocktailRepository.getCocktailAll().collectLatest { cocktails ->
        _cocktailList.value = cocktails
      }
    }
  }

  fun insertBookmark(idx: Int) = viewModelScope.launch {
    cocktailRepository.insertBookmark(BookmarkIdx(idx = idx))
  }

  fun deleteBookmark(idx: Int) = viewModelScope.launch {
    recentlyDeleteCocktail = BookmarkIdx(idx = idx)
    cocktailRepository.deleteBookmark(BookmarkIdx(idx = idx))
  }

  fun restoreCocktail() {
    viewModelScope.launch {
      cocktailRepository.insertBookmark(
        recentlyDeleteCocktail?.copy() ?: return@launch
      )
      recentlyDeleteCocktail = null
    }
  }
}