@file:OptIn(DelicateCoroutinesApi::class)

package com.compose.cocktaildakk_compose.ui.search

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
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

  val recentSearchList = mutableStateOf(emptyList<RecentStr>())

  val textFieldValue =
    MutableStateFlow(
      TextFieldValue(
        text = VISIBLE_SEARCH_STR.value,
        selection = TextRange(VISIBLE_SEARCH_STR.value.length)
      )
    )

//  private var _pagingCocktailList = MutableStateFlow<PagingData<Cocktail>>(PagingData.empty())
//  val pagingCocktailList = _pagingCocktailList.asStateFlow()

  val pagingCocktailList = textFieldValue.debounce(200)
    .distinctUntilChanged()
    .flatMapLatest {
      cocktailRepository.cocktailPaging(textFieldValue.value.text)
    }
//    .flatMapLatest {
//      queryFromDb(it)
//    }


  init {
    viewModelScope.launch {
      searchRepo.getRecentSearchAll().collectLatest { recentList ->
        recentSearchList.value = recentList
      }
    }
  }

//  suspend fun getCocktailPaging(searchStr: String) {
//    viewModelScope.launch {
//      _pagingCocktailList.value = PagingData.empty()
//      cocktailRepository.cocktailPaging(searchStr).cachedIn(viewModelScope)
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