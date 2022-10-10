@file:OptIn(DelicateCoroutinesApi::class)

package com.compose.cocktaildakk_compose.ui.search

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
class SearchViewModel @Inject constructor(
  private val searchRepo: SearchRepository,
  private val cocktailRepository: CocktailRepository,
) : ViewModel() {

  val listState: LazyListState = LazyListState()
  private val TAG = "SearchViewModel"

  var offset: Int = 0
  var index: Int = 0

  //  var lastSearchedStr: String? = null
//  var totalCnt = mutableStateOf(0)
  val recentSearchList = mutableStateOf(emptyList<RecentStr>())

  //  private var _pagingCocktailList = MutableStateFlow<PagingData<Cocktail>>(PagingData.empty())
//  var pagingCocktailList = _pagingCocktailList.asStateFlow()
  private val _cocktailList = mutableStateOf(emptyList<Cocktail>())
  val cocktailList: State<List<Cocktail>> get() = _cocktailList

  init {
    viewModelScope.launch {
      searchRepo.getRecentSearchAll().collectLatest { recentList ->
        recentSearchList.value = recentList
      }
    }
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

//  private fun cocktailPaging(): Flow<PagingData<Cocktail>> {
//    return Pager(
//      config = PagingConfig(
//        pageSize = 7,
//      ),
//      pagingSourceFactory = {
//        CocktailPagingSource(
//          cocktailDao = cocktailDao,
//          searchStr = VISIBLE_SEARCH_STR.value
//        )
//      }
//    ).flow
//  }

//  suspend fun getCocktailPaging() {
//    viewModelScope.launch {
//      _pagingCocktailList.value = PagingData.empty()
//      cocktailPaging().cachedIn(viewModelScope)
//        .collectLatest {
//          _pagingCocktailList.value = it
//        }
//    }
//  }


  fun addSearchStr(searchStr: String) = viewModelScope.launch {
    val dbItem = recentSearchList.value.find {
      it.value == searchStr
    }
    if (dbItem == null) {
      searchRepo.addSearchStr(searchStr)
    }
  }

  fun removeSearchStr(id: Int) = viewModelScope.launch {
    recentSearchList.value.find {
      it.id == id
    }?.let {
      searchRepo.removeSearchStr(it)
    }
  }

  fun removeAllSearchStr() = viewModelScope.launch {
    searchRepo.removeAllSearchStr()
  }

}