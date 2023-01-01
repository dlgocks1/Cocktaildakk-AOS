package com.compose.cocktaildakk_compose.ui.onboarding

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.compose.cocktaildakk_compose.*
import com.compose.cocktaildakk_compose.domain.model.KeywordTag
import com.compose.cocktaildakk_compose.domain.model.UserCocktailWeight
import com.compose.cocktaildakk_compose.domain.model.UserInfo
import com.compose.cocktaildakk_compose.domain.repository.CocktailRepository
import com.compose.cocktaildakk_compose.domain.repository.UserInfoRepository
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnboardViewModel @Inject constructor(
    private val userInfoRepository: UserInfoRepository,
    private val cocktailRepository: CocktailRepository,
    private val firestore: FirebaseFirestore,
) : ViewModel() {

    var nickname: String = DEFAULT_NICKNAME
    var level: Int = DEFAULT_LEVEL
    var sex: String = DEFAULT_SEX
    var age = DEFAULT_AGE
    var base = DEFAULT_BASE
    var keyword = DEFAULT_KEYWORD

    private val _keywordTagList = mutableStateOf(emptyList<KeywordTag>())
    val keywordTagList: State<List<KeywordTag>>
        get() = _keywordTagList

    init {
        viewModelScope.launch {
            cocktailRepository.getAllKeyword().collectLatest {
                _keywordTagList.value = it
            }
        }
    }

    data class TagList(
        val text: String = "",
        var isSelected: Boolean = false,
    )

    fun insertUserinfo(onFinished: () -> Unit) {
        val params = UserInfo(
            age = age,
            sex = sex,
            level = level,
            keyword = keyword,
            base = base,
            nickname = nickname,
        )
        val data = params.toHashMap()
        setCocktailWeight()
        setFirebaseUserKey(data, params, onFinished)
    }

    private fun setFirebaseBookmarkKey(params: UserInfo, onFinished: () -> Unit) {
        firestore.collection(USER_DATA)
            .document(params.userKey)
            .collection(BOOKMARK_EN)
            .add(hashMapOf(BOOKMARKS_EN to emptyList<Int>()))
            .addOnSuccessListener {
                params.bookmarkKey = it.id
                setUserDataToDatabase(params, onFinished)
            }
    }

    private fun setFirebaseUserKey(
        data: HashMap<String, Any>,
        params: UserInfo,
        onFinished: () -> Unit,
    ) {
        firestore.collection(USER_DATA)
            .add(data)
            .addOnSuccessListener {
                params.userKey = it.id
                setFirebaseBookmarkKey(params, onFinished)
            }
    }

    private fun setCocktailWeight() {
        viewModelScope.launch {
            userInfoRepository.insertCocktailWeight(
                userCocktailWeight = UserCocktailWeight(),
            )
        }
    }

    private fun setUserDataToDatabase(params: UserInfo, onFinished: () -> Unit) {
        viewModelScope.launch {
            async {
                userInfoRepository.insertUserInfo(params)
            }.join()
            onFinished()
        }
    }
}
