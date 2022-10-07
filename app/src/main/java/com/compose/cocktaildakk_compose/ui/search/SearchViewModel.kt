package com.compose.cocktaildakk_compose.ui.search

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.compose.cocktaildakk_compose.domain.model.Cocktail
import com.compose.cocktaildakk_compose.domain.model.RecentStr
import com.compose.cocktaildakk_compose.domain.repository.CocktailRepository
import com.compose.cocktaildakk_compose.domain.repository.SearchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
  private val searchRepo: SearchRepository,
  private val cocktailRepository: CocktailRepository,
) : ViewModel() {

  private val TAG = "SearchViewModel"
  private val _searchStrResult: MutableState<String> = mutableStateOf("")
  val searchStrResult: State<String> get() = _searchStrResult
  val _recentSearchList = mutableStateOf(emptyList<RecentStr>())

  private val _searchList = mutableStateOf(emptyList<Cocktail>())
  val searchList: State<List<Cocktail>> get() = _searchList

  init {
    viewModelScope.launch {
      searchRepo.getRecentSearchAll().collect { recentList ->
        _recentSearchList.value = recentList
      }
    }
  }

  suspend fun getCocktails() = viewModelScope.launch {
    cocktailRepository.getCocktailAll().collect() {
      _searchList.value = it
    }
  }

  fun handleUpdateSearchResult(str: String) {
    _searchStrResult.value = str
  }

  fun addSearchStr(searchStr: String) = viewModelScope.launch {
    val dbItem = _recentSearchList.value.find {
      it.value == searchStr
    }
    if (dbItem == null) {
      searchRepo.addSearchStr(searchStr)
    }
  }

  fun removeSearchStr(id: Int) = viewModelScope.launch {
    _recentSearchList.value.find {
      it.id == id
    }?.let {
      searchRepo.removeSearchStr(it)
    }
  }

  fun removeAllSearchStr() = viewModelScope.launch {
    searchRepo.removeAllSearchStr()
  }

}