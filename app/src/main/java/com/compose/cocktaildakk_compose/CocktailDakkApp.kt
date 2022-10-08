package com.compose.cocktaildakk_compose

import android.app.Application
import androidx.compose.runtime.mutableStateOf
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class CocktailDakkApp : Application() {

}

object SingletonObject {
  var VISIBLE_SEARCH_STR = mutableStateOf("")
}