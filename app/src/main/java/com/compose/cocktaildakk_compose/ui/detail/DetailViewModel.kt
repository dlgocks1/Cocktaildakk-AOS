package com.compose.cocktaildakk_compose.ui.detail

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.compose.cocktaildakk_compose.domain.model.Cocktail
import com.compose.cocktaildakk_compose.domain.model.Review
import com.compose.cocktaildakk_compose.domain.repository.CocktailRepository
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObjects
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val cocktailRepository: CocktailRepository,
    private val firestore: FirebaseFirestore,
) : ViewModel() {

    private val _cocktailDetail = mutableStateOf(Cocktail())
    val cocktailDetail: State<Cocktail> = _cocktailDetail

    val reviewContents = mutableStateListOf<Review>()

    fun getDetail(idx: Int) = viewModelScope.launch {
        cocktailRepository.getCocktail(idx).collectLatest {
            _cocktailDetail.value = it
        }
    }

    fun getReview(idx: Int) {
        firestore.collection("review")
            .whereEqualTo("idx", idx)
            .get()
            .addOnSuccessListener { query ->
                query?.let {
                    val reviewList = it.toObjects<Review>()
                    if (reviewList.isNotEmpty()) {
                        reviewContents.clear()
                        reviewContents.addAll(reviewList)
                    }
                }
            }.addOnFailureListener {
            }
    }
}
