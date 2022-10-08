package com.compose.cocktaildakk_compose.ui.search

import android.util.Log
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.rememberScrollState
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
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
  private val searchRepo: SearchRepository,
  private val cocktailRepository: CocktailRepository,
) : ViewModel() {

  var offset: Int = 0
  var index: Int = 0
  private val TAG = "SearchViewModel"
  private val _searchStrResult: MutableState<String> = mutableStateOf("")
  val searchStrResult: State<String> get() = _searchStrResult
  val _recentSearchList = mutableStateOf(emptyList<RecentStr>())

  private val _cocktailList = mutableStateOf(emptyList<Cocktail>())
  val cocktailList: State<List<Cocktail>> get() = _cocktailList

  init {
    viewModelScope.launch {
      searchRepo.getRecentSearchAll().collect { recentList ->
        _recentSearchList.value = recentList
      }
    }
  }

  suspend fun getCocktails(query: String = "") = viewModelScope.launch {
    cocktailRepository.queryCocktail(query).collect() {
      Log.i(TAG, it.toString())
      _cocktailList.value = it
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

  fun toggleBookmark(idx: Int) = viewModelScope.launch {
    _cocktailList.value.find {
      it.idx == idx
    }?.let {
      cocktailRepository.updateCocktail(it.copy(isBookmark = !it.isBookmark))
    }
  }

}