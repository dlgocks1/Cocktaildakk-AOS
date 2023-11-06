package com.compose.cocktaildakk_compose

import android.app.Application
import androidx.compose.runtime.mutableStateOf
import com.compose.cocktaildakk_compose.domain.model.Cocktail
import com.google.firebase.FirebaseApp
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class CocktailDakkApp : Application()

object SingletonObject {
    var VISIBLE_SEARCH_STR = mutableStateOf("")
    var MAIN_REC_LIST = mutableStateOf(emptyList<Cocktail>())
}
