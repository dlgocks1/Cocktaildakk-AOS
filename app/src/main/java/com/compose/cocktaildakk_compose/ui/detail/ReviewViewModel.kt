package com.compose.cocktaildakk_compose.ui.detail

import android.graphics.Bitmap
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.compose.cocktaildakk_compose.data.data_source.GalleryPagingSource
import com.compose.cocktaildakk_compose.data.data_source.GalleryPagingSource.Companion.PAGING_SIZE
import com.compose.cocktaildakk_compose.domain.model.GalleryImage
import com.compose.cocktaildakk_compose.domain.model.Review
import com.compose.cocktaildakk_compose.domain.repository.ImageRepository
import com.compose.cocktaildakk_compose.domain.repository.ReviewRepository
import com.compose.cocktaildakk_compose.domain.repository.UserInfoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


@HiltViewModel
class ReviewViewModel @Inject constructor(
    private val imageRepository: ImageRepository,
    private val userInfoRepository: UserInfoRepository,
    private val reviewRepository: ReviewRepository
) : ViewModel() {

    val userContents = mutableStateOf(TextFieldValue(""))

    private val _modifyingImage = mutableStateOf<GalleryImage?>(null)
    val modifyingImage: State<GalleryImage?> = _modifyingImage

    private val _selectedImages = mutableStateListOf<CroppingImage>()
    val selectedImages: SnapshotStateList<CroppingImage> = _selectedImages

    private val _selecetedStatus = mutableStateOf(ImageCropStatus.WAITING)
    val selecetedStatus: State<ImageCropStatus> = _selecetedStatus

    private val _rankScore = mutableStateOf(0)
    val rankScore: State<Int> get() = _rankScore

    private val _isLoading = mutableStateOf<Boolean>(false)
    val isLoading: State<Boolean> = _isLoading

    private val _loadingState = mutableStateOf(0)
    val loadingState: State<Int> = _loadingState

    // 갤러리 이미지 페이징 소스
    val pagingCocktailList: Flow<PagingData<GalleryImage>> =
        Pager(
            config = PagingConfig(
                pageSize = PAGING_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                GalleryPagingSource(imageRepository)
            }
        ).flow.cachedIn(viewModelScope)

    fun setLoading(isLoading: Boolean) {
        _isLoading.value = isLoading
    }

    fun setRankScore(score: Int) {
        _rankScore.value = score
    }

    fun setCropStatus(status: ImageCropStatus) {
        _selecetedStatus.value = status
    }

    fun setModifyingImage(image: GalleryImage) {
        _modifyingImage.value = image
    }

    fun addSelectedImage(id: Long, image: Bitmap) {
        _selectedImages.add(CroppingImage(id, image))
    }

    fun deleteImage(id: Long) {
        val removedImage = _selectedImages.find { it.id == id }
        removedImage?.let {
            _selectedImages.remove(removedImage)
        }
    }

    fun addCropedImage(secondScreenResult: List<CroppingImage>?) {
        secondScreenResult?.let { _selectedImages.addAll(it) }
    }

    fun addReviewToFirebase(
        idx: Int,
        images: List<CroppingImage>,
        onSuccess: () -> Unit,
        onFailed: () -> Unit,
    ) {
        setLoading(true)
        viewModelScope.launch {
            val userinfo = userInfoRepository.getUserInfo().first()
            require(userinfo != null) {
                "유저 정보가 등록되어 있지 않으면 어떻게하죠..?"
            }
            val downloadList = withContext(Dispatchers.IO) {
                reviewRepository.putDataToStorage(
                    setLoadingState = {
                        _loadingState.value = it
                    },
                    images = images.map { it.croppedBitmap },
                    userinfo = userinfo
                )
            }
            val params = Review(
                idx = idx,
                rankScore = rankScore.value,
                images = downloadList,
                contents = userContents.value.text,
                userInfo = userinfo
            )
            reviewRepository.writeReview(review = params,
                onSuccess = {
                    onSuccess()
                }, onFailed = {
                    onFailed()
                })
        }
    }

    data class CroppingImage(
        val id: Long,
        val croppedBitmap: Bitmap
    )

    enum class ImageCropStatus {
        WAITING,
        MODIFYING,
        CROPPING;

        fun isModifying(action: () -> Unit) {
            if (this == MODIFYING) {
                action()
            }
        }

        fun isCropping(action: () -> Unit) {
            if (this == CROPPING) {
                action()
            }
        }
    }

}