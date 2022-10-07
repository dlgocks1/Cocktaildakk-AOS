package com.compose.cocktaildakk_compose.ui.onboarding

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class OnboardViewModel @Inject constructor() : ViewModel() {
  data class TagList(
    val text: String = "",
    var isSelected: Boolean = false
  )

}