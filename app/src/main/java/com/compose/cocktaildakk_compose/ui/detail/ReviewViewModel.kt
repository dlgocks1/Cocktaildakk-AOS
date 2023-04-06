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
import com.compose.cocktaildakk_compose.di.DispatcherModule
import com.compose.cocktaildakk_compose.domain.model.Cocktail
import com.compose.cocktaildakk_compose.domain.model.GalleryImage
import com.compose.cocktaildakk_compose.domain.model.Review
import com.compose.cocktaildakk_compose.domain.repository.CocktailRepository
import com.compose.cocktaildakk_compose.domain.repository.ImageRepository
import com.compose.cocktaildakk_compose.domain.repository.ReviewRepository
import com.compose.cocktaildakk_compose.domain.repository.UserInfoRepository
import com.compose.cocktaildakk_compose.ui.detail.model.CroppingImage
import com.compose.cocktaildakk_compose.ui.detail.model.ImageCropStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ReviewViewModel @Inject constructor(
    private val imageRepository: ImageRepository,
    private val userInfoRepository: UserInfoRepository,
    private val reviewRepository: ReviewRepository,
    private val cocktailRepository: CocktailRepository,
    @DispatcherModule.IoDispatcher private val ioDispatcher: CoroutineDispatcher,
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

    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    private val _loadingState = mutableStateOf(0)
    val loadingState: State<Int> = _loadingState

    private val _directories = mutableStateListOf<Pair<String, String?>>("최근사진" to null)
    val directories get() = _directories

    private val _currentDirectory = mutableStateOf<Pair<String, String?>>("최근사진" to null)
    val currentDirectory: State<Pair<String, String?>> = _currentDirectory

    private val _customGalleryPhotoList =
        MutableStateFlow<PagingData<GalleryImage>>(PagingData.empty())
    val customGalleryPhotoList: StateFlow<PagingData<GalleryImage>> =
        _customGalleryPhotoList.asStateFlow()

    private val _cocktailDetail = mutableStateOf(Cocktail())
    val cocktailDetail: State<Cocktail> = _cocktailDetail

    fun getDetail(idx: Int) = viewModelScope.launch {
        cocktailRepository.getCocktail(idx).collectLatest {
            _cocktailDetail.value = it
        }
    }

    fun getGalleryPagingImages() = viewModelScope.launch {
        _customGalleryPhotoList.value = PagingData.empty()
        Pager(
            config = PagingConfig(
                pageSize = PAGING_SIZE,
                enablePlaceholders = true,
            ),
            pagingSourceFactory = {
                GalleryPagingSource(
                    imageRepository = imageRepository,
                    currnetLocation = currentDirectory.value.second,
                )
            },
        ).flow.cachedIn(viewModelScope).collectLatest {
            _customGalleryPhotoList.value = it
        }
    }

    fun setCurrentDirectory(location: Pair<String, String?>) {
        _currentDirectory.value = location
    }

    fun getDirectory() {
        imageRepository.getFolderList().map {
            _directories.add(it.split("/").last() to it)
        }
    }

    private fun setLoading(isLoading: Boolean) {
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

    fun removeSelectedImage(id: Long) {
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
                "유저 정보가 등록되어 있지 않아요."
            }
            val downloadList = withContext(ioDispatcher) {
                reviewRepository.putDataToStorage(
                    setLoadingState = {
                        _loadingState.value = it
                    },
                    images = images.map { it.croppedBitmap },
                    userinfo = userinfo,
                )
            }
            val params = Review(
                idx = idx,
                rankScore = rankScore.value,
                images = downloadList,
                contents = userContents.value.text,
                userInfo = userinfo,
            )
            reviewRepository.writeReview(
                review = params,
                onSuccess = {
                    onSuccess()
                },
                onFailed = {
                    onFailed()
                },
            )
        }
    }


}
