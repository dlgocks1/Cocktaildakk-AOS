@file:OptIn(DelicateCoroutinesApi::class)

package com.compose.cocktaildakk_compose.ui.search.searchResult

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.compose.LazyPagingItems
import com.compose.cocktaildakk_compose.SingletonObject.VISIBLE_SEARCH_STR
import com.compose.cocktaildakk_compose.data.data_source.CocktailDao
import com.compose.cocktaildakk_compose.data.data_source.CocktailPagingSource
import com.compose.cocktaildakk_compose.domain.model.BookmarkIdx
import com.compose.cocktaildakk_compose.domain.model.Cocktail
import com.compose.cocktaildakk_compose.domain.model.RecentStr
import com.compose.cocktaildakk_compose.domain.repository.CocktailRepository
import com.compose.cocktaildakk_compose.domain.repository.SearchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchResultViewModel @Inject constructor(
  private val searchRepo: SearchRepository,
  private val cocktailRepository: CocktailRepository,
) : ViewModel() {

  val listState: LazyListState = LazyListState()

  private val _cocktailList = mutableStateOf(emptyList<Cocktail>())
  val cocktailList: State<List<Cocktail>> get() = _cocktailList

  init {

    viewModelScope.launch {
      cocktailRepository.queryCocktail(VISIBLE_SEARCH_STR.value).collectLatest {
        _cocktailList.value = it
      }
    }
  }

  suspend fun getCocktails(query: String = "") = viewModelScope.launch {
    cocktailRepository.queryCocktail(query).collect() {
      _cocktailList.value = it
    }
  }

//  suspend fun getTotalCount(str: String) = viewModelScope.launch {
//    totalCnt.value = cocktailDao.getCocktailCounts(str).first()
//  }


}