package com.compose.cocktaildakk_compose.ui.detail

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.compose.cocktaildakk_compose.domain.model.Cocktail
import com.compose.cocktaildakk_compose.domain.model.GalleryImage
import com.compose.cocktaildakk_compose.domain.repository.CocktailRepository
import com.compose.cocktaildakk_compose.domain.repository.ImageRepository
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val imageRepository: ImageRepository,
    private val cocktailRepository: CocktailRepository,
    private val firestore: FirebaseFirestore,
) : ViewModel() {

    private val _cocktailDetail = mutableStateOf(Cocktail())
    val cocktailDetail: State<Cocktail> = _cocktailDetail

    val _allImages = mutableStateListOf<GalleryImage>()

    fun getDetail(idx: Int) = viewModelScope.launch {
        cocktailRepository.getCocktail(idx).collectLatest {
            _cocktailDetail.value = it
        }
    }

    fun getReview(idx: Int) {
        firestore.collection("cocktailList")
            .document("999")
            .get()
            .addOnSuccessListener {
                Log.i("DetailViewModel", it.toString())
            }
            .addOnFailureListener {
                Log.i("DetailViewModel", it.toString())
            }
    }

    fun getAllImage() {
        _allImages.addAll(imageRepository.getAllPhotos())
        Log.i("detailViewModel", _allImages.toString())
        Log.i("detailViewModel", imageRepository.getAllPhotos().toString())
    }

}