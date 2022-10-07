package com.compose.cocktaildakk_compose.ui.splash

import android.util.Log
import androidx.datastore.preferences.core.Preferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.compose.cocktaildakk_compose.domain.model.Cocktail
import com.compose.cocktaildakk_compose.domain.model.CocktailVersion
import com.compose.cocktaildakk_compose.domain.repository.CocktailRepository
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObjects
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
  private val firestore: FirebaseFirestore,
  private val repository: CocktailRepository
) : ViewModel() {

  suspend fun getVersion() = withContext(Dispatchers.IO) {
    repository.getCocktailVersion().first()
  }

  suspend fun checkCocktailVersion(
    onStart: () -> Unit = {},
    onEnd: () -> Unit = {},
  ) {
    onStart()
    firestore.collection("metaData")
      .get()
      .addOnSuccessListener { query ->
        query?.let {
          it.toObjects<CocktailVersion>().first().let {
            viewModelScope.launch {
              if (it.version == getVersion()) {
                onEnd()
              } else {
                downloadCocktailList(onEnd)
                repository.setCocktailVersion(it.version)
              }
            }
          }
        }
      }
      .addOnFailureListener { exception ->
        throw exception
      }
  }

  suspend fun downloadCocktailList(
    onEnd: () -> Unit,
  ) {
    Log.i("SplashScreen", "DownLoad Start")
    firestore.collection("cocktailList_test")
      .orderBy("idx")
      .get()
      .addOnSuccessListener { document ->
        document?.let {
          it.toObjects<Cocktail>().forEach() {
            viewModelScope.launch {
              repository.addCocktail(it)
            }
          }
          onEnd()
        }
      }
      .addOnFailureListener { exception ->
        throw exception
      }
  }

}