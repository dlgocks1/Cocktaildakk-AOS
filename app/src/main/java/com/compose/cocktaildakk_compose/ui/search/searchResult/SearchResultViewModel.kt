package com.compose.cocktaildakk_compose.ui.search.searchResult

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.compose.cocktaildakk_compose.SingletonObject.VISIBLE_SEARCH_STR
import com.compose.cocktaildakk_compose.domain.model.Cocktail
import com.compose.cocktaildakk_compose.domain.repository.CocktailRepository
import com.compose.cocktaildakk_compose.domain.repository.SearchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
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

    fun getCocktails(query: String = "") = viewModelScope.launch {
        cocktailRepository.queryCocktail(query).collect {
            _cocktailList.value = it
        }
    }

}