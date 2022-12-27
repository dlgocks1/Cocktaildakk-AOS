@file:OptIn(ExperimentalAnimationApi::class)

package com.compose.cocktaildakk_compose.ui.detail.gallery

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import androidx.compose.animation.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import coil.compose.rememberAsyncImagePainter
import com.compose.cocktaildakk_compose.R
import com.compose.cocktaildakk_compose.ui.ApplicationState
import com.compose.cocktaildakk_compose.ui.theme.Color_Default_Backgounrd
import com.compose.cocktaildakk_compose.ui.utils.showSnackbar
import com.naver.android.helloyako.imagecrop.view.ImageCropView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


@Composable
fun GalleryScreen(
    appState: ApplicationState,
    viewModel: GalleryViewModel = hiltViewModel()
) {

    val pagingItems = viewModel.pagingCocktailList.collectAsLazyPagingItems()
    val scope = rememberCoroutineScope()
    LaunchedEffect(Unit) {
        val secondScreenResult = appState.navController.previousBackStackEntry
            ?.savedStateHandle
            ?.getLiveData<List<GalleryViewModel.CroppingImage>>("bitmap_images")
            ?.value
        viewModel.addCropedImage(secondScreenResult)
    }


    Column(modifier = Modifier.background(Color_Default_Backgounrd)) {
        TopBar(appState.navController, viewModel)
        SelectedImages(viewModel)
        CustomImageCropView(appState, scope, viewModel)
        LazyRow(
            modifier = Modifier
                .fillMaxSize(1f),
        ) {
            items(pagingItems) { images ->
                images?.let {
                    val isSelecetd = viewModel.selectedImages.find { it.id == images.id } != null
                    Box {
                        Image(
                            modifier = Modifier
                                .aspectRatio(1f)
                                .padding(2.dp)
                                .animateContentSize()
                                .clickable {
                                    viewModel.setModifyingImage(images)
                                },
                            painter = rememberAsyncImagePainter(BitmapFactory.decodeFile(images.filepath)),
                            contentDescription = "리스트 이미지",
                            contentScale = ContentScale.Crop,
                            alpha = if (isSelecetd) 0.5f else 1f
                        )
                        if (isSelecetd) {
                            Icon(
                                modifier = Modifier
                                    .padding(16.dp)
                                    .size(24.dp)
                                    .clip(CircleShape)
                                    .background(
                                        Color_Default_Backgounrd
                                    )
                                    .align(Alignment.TopEnd)
                                    .clickable {
                                        viewModel.deleteImage(images.id)
                                    },
                                painter = painterResource(id = R.drawable.ic_baseline_close_24),
                                contentDescription = "이미지 취소",
                                tint = Color.White
                            )
                        }
                    }
                }
            }

        }
    }
}

@Composable
fun SelectedImages(viewmodel: GalleryViewModel) {
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    Column(
        Modifier
            .fillMaxWidth(1f)
            .padding(start = 20.dp, end = 20.dp, bottom = 10.dp)
    ) {
        Text(text = "선택된 이미지 ${viewmodel.selectedImages.size} / 5", color = Color.White)
        Spacer(modifier = Modifier.height(10.dp))
        Row(
            modifier = Modifier.horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            viewmodel.selectedImages.map {
                Box {
                    Image(
                        painter = rememberAsyncImagePainter(it.croppedBitmap),
                        contentDescription = null,
                        modifier = Modifier
                            .size(screenWidth.div(6))
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop
                    )
                    Icon(
                        modifier = Modifier
                            .size(20.dp)
                            .clip(CircleShape)
                            .background(
                                Color_Default_Backgounrd
                            )
                            .align(Alignment.TopEnd)
                            .clickable {
                                viewmodel.deleteImage(it.id)
                            },
                        painter = painterResource(id = R.drawable.ic_baseline_close_24),
                        contentDescription = "이미지 취소",
                        tint = Color.White
                    )
                }
            }
        }
    }
}


@Composable
private fun CustomImageCropView(
    appState: ApplicationState,
    scope: CoroutineScope,
    viewmodel: GalleryViewModel
) {

    val modifyingImage = viewmodel.modifyingImage.value
    val isSelected = viewmodel.selectedImages.find {
        it.id == viewmodel.modifyingImage.value?.id
    } != null

    Box(Modifier.height(IntrinsicSize.Min)) {
        AndroidView(
            modifier = Modifier
                .fillMaxWidth(1f)
                .aspectRatio(1f),
            factory = { context ->
                val view = LayoutInflater.from(context).inflate(R.layout.image_crop_view, null)
                val imageCropView = view.findViewById<ImageCropView>(R.id.image_crop_view)
                imageCropView.apply {
                    setAspectRatio(1, 1)
                }
                imageCropView
            },
            update = { view ->
                modifyingImage?.let {
                    view.setImageFilePath(it.filepath)
                    view.zoomTo(1f, 200f)
                }
                if (modifyingImage != null) {
                    viewmodel.selecetedStatus.value.isCropping {
                        viewmodel.addSelectedImage(modifyingImage.id, view.croppedImage)
                    }
                    viewmodel.selecetedStatus.value.isModifying {
                        val index =
                            viewmodel.selectedImages.indexOf(viewmodel.selectedImages.find { it.id == modifyingImage.id })
                        viewmodel.selectedImages[index] =
                            GalleryViewModel.CroppingImage(modifyingImage.id, view.croppedImage)
                    }
                    viewmodel.setCropStatus(GalleryViewModel.ImageCropStatus.WAITING)
                }
            }
        )

        if (modifyingImage != null) {
            Box(
                modifier = Modifier
                    .padding(20.dp)
                    .align(Alignment.BottomEnd)
                    .clip(RoundedCornerShape(30))
                    .border(1.dp, Color.White, RoundedCornerShape(30))
                    .background(Color_Default_Backgounrd),
            ) {
                Text(
                    text = if (isSelected) "수정하기" else "추가하기", modifier = Modifier
                        .clip(RoundedCornerShape(30.dp))
                        .padding(20.dp, 5.dp)
                        .background(
                            Color_Default_Backgounrd
                        )
                        .clickable {
                            if (isSelected) {
                                viewmodel.setCropStatus(GalleryViewModel.ImageCropStatus.MODIFYING)
                            } else {
                                if (viewmodel.selectedImages.size <= 4) {
                                    viewmodel.setCropStatus(GalleryViewModel.ImageCropStatus.CROPPING)
                                } else {
                                    scope.launch {
                                        appState.scaffoldState.showSnackbar("이미지는 5개 이하로 선택해 주세요.")
                                    }
                                }
                            }
                        },
                    color = Color.White
                )
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize(1f),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "업로드할 이미지를 선택해 주세요.",
                    color = Color.White,
                    fontSize = 18.sp,
                    modifier = Modifier
                        .fillMaxWidth(1f),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}


@Composable
private fun TopBar(
    navController: NavController,
    galleryViewModel: GalleryViewModel
) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .height(30.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = "취소", color = Color.White)
        Text(
            text = "현재위치",
            color = Color.White,
            textAlign = TextAlign.Center,
            modifier = Modifier.weight(1f)
        )
        Text(text = "확인",
            color = Color.White,
            modifier = Modifier.clickable {
                navController.previousBackStackEntry
                    ?.savedStateHandle
                    ?.set(
                        "bitmap_images",
//                        galleryViewModel.selectedImages.map { it.croppedBitmap }.toList()
                        galleryViewModel.selectedImages.toList()
                    )
                navController.popBackStack()
            })
    }
}

@Composable
@Preview
fun PreviewGalleryScreen() {
//    GalleryScreen()
}
