package com.compose.cocktaildakk_compose.ui.Search

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.compose.cocktaildakk_compose.domain.model.RecentStr
import com.compose.cocktaildakk_compose.domain.repository.SearchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
	private val searchRepo: SearchRepository
) : ViewModel() {

	private val _searchStr = mutableStateOf("")
	val searchStr: State<String> get() = _searchStr

	private val _recentSearchList = mutableStateOf(emptyList<RecentStr>())
	val recentSearchList: State<List<RecentStr>> get() = _recentSearchList

	fun handleUpdateSearchStr(str: String) {
		_searchStr.value = str
	}

	fun addSearchStr(searchStr: String) = viewModelScope.launch {
		searchRepo.addSearchStr(searchStr)
	}

	fun getSearchStr() = viewModelScope.launch {
		searchRepo.getResentSearchAll().collectLatest {
			_recentSearchList.value = it
		}
	}

}