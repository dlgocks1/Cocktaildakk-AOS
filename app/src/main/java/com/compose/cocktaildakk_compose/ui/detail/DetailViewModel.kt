package com.compose.cocktaildakk_compose.ui.detail

import android.util.Log
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
class DetailViewModel @Inject constructor(
  private val cocktailRepository: CocktailRepository
) : ViewModel() {

  private val _cocktailDetail = mutableStateOf(Cocktail())
  val cocktailDetail: State<Cocktail> = _cocktailDetail

  fun getDetail(idx: Int) = viewModelScope.launch {
    cocktailRepository.getCocktail(idx).collectLatest {
      Log.i("Detail", it.toString())
      _cocktailDetail.value = it
    }
  }

}