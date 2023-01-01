package com.compose.cocktaildakk_compose.ui.bookmark

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.compose.cocktaildakk_compose.BOOKMARKS_EN
import com.compose.cocktaildakk_compose.BOOKMARK_EN
import com.compose.cocktaildakk_compose.NOT_EXIST_FIREBASE_KEY
import com.compose.cocktaildakk_compose.USER_DATA
import com.compose.cocktaildakk_compose.domain.model.BookmarkIdx
import com.compose.cocktaildakk_compose.domain.model.Cocktail
import com.compose.cocktaildakk_compose.domain.repository.CocktailRepository
import com.compose.cocktaildakk_compose.domain.repository.UserInfoRepository
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.lang.IllegalStateException
import javax.inject.Inject

@HiltViewModel
class BookmarkViewModel @Inject constructor(
    private val cocktailRepository: CocktailRepository,
    private val userInfoRepository: UserInfoRepository,
    private val firestore: FirebaseFirestore
) : ViewModel() {

    private val _cocktailList = mutableStateOf(emptyList<Cocktail>())
    val cocktailList: State<List<Cocktail>> = _cocktailList
    private var recentlyDeleteCocktail: BookmarkIdx? = null
    val bookmarkList = mutableStateOf(emptyList<BookmarkIdx>())

    init {
        viewModelScope.launch {
            cocktailRepository.getAllBookmark().collectLatest {
                bookmarkList.value = it
            }
        }
        viewModelScope.launch {
            cocktailRepository.getCocktailAll().collectLatest { cocktails ->
                _cocktailList.value = cocktails
            }
        }
    }

    fun insertBookmark(idx: Int) = viewModelScope.launch {
        async {
            cocktailRepository.insertBookmark(BookmarkIdx(idx = idx))
        }.join()
        updateBookmarkToFireBase()
    }


    fun deleteBookmark(idx: Int) = viewModelScope.launch {
        recentlyDeleteCocktail = BookmarkIdx(idx = idx)
        cocktailRepository.deleteBookmark(BookmarkIdx(idx = idx))
        updateBookmarkToFireBase()
    }

    fun restoreCocktail() = viewModelScope.launch {
        cocktailRepository.insertBookmark(recentlyDeleteCocktail?.copy() ?: return@launch)
        recentlyDeleteCocktail = null
        updateBookmarkToFireBase()
    }

    private suspend fun updateBookmarkToFireBase() = viewModelScope.launch {
        val defferedBookmark = async {
            cocktailRepository.getAllBookmark().first()
        }
        val defferedUserData = async {
            userInfoRepository.getUserInfo().first()
        }
        firestore.collection(USER_DATA)
            .document(
                defferedUserData.await()?.userKey
                    ?: throw IllegalStateException(NOT_EXIST_FIREBASE_KEY)
            ).collection(BOOKMARK_EN)
            .document(
                defferedUserData.await()?.bookmarkKey
                    ?: throw IllegalStateException(NOT_EXIST_FIREBASE_KEY)
            ).update(
                hashMapOf(
                    BOOKMARKS_EN to defferedBookmark.await().map { it.idx }) as Map<String, Any>
            )
    }

}