@file:OptIn(
    ExperimentalAnimationApi::class, ExperimentalFoundationApi::class,
    ExperimentalFoundationApi::class
)

package com.compose.cocktaildakk_compose.ui.detail.gallery

import android.view.LayoutInflater
import androidx.compose.animation.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.rememberAsyncImagePainter
import com.compose.cocktaildakk_compose.R
import com.compose.cocktaildakk_compose.domain.model.GalleryImage
import com.compose.cocktaildakk_compose.ui.ApplicationState
import com.compose.cocktaildakk_compose.ui.detail.ReviewViewModel
import com.compose.cocktaildakk_compose.ui.theme.Color_Default_Backgounrd
import com.compose.cocktaildakk_compose.ui.theme.Color_White_70
import com.compose.cocktaildakk_compose.ui.theme.items
import com.compose.cocktaildakk_compose.ui.utils.showSnackbar
import com.naver.android.helloyako.imagecrop.view.ImageCropView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


@Composable
fun GalleryScreen(
    appState: ApplicationState,
    viewModel: ReviewViewModel = hiltViewModel()
) {

//    val pagingItems = viewModel.pagingCocktailList.collectAsLazyPagingItems()
    val pagingItems = viewModel.customGalleryPhotoList.collectAsLazyPagingItems()
    val scope = rememberCoroutineScope()

    LaunchedEffect(viewModel.currentLocation.value) {
        viewModel.getGalleryPagingImages()
    }

    LaunchedEffect(Unit) {
        val secondScreenResult = appState.navController.previousBackStackEntry
            ?.savedStateHandle
            ?.getLiveData<List<ReviewViewModel.CroppingImage>>("bitmap_images")
            ?.value
        viewModel.addCropedImage(secondScreenResult)
        viewModel.getDirectory()
    }


    Column(modifier = Modifier.background(Color_Default_Backgounrd)) {
        TopBar(appState, scope, viewModel)
        SelectedImages(viewModel)
        CustomImageCropView(appState, scope, viewModel)
        if (pagingItems.itemCount == 0) {
            Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.Center) {
                Text(
                    text = "이미지가 존재하지 않습니다.",
                    fontSize = 19.sp,
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        } else {
            LazyVerticalGrid(
                modifier = Modifier
                    .weight(1f)
                    .background(Color_Default_Backgounrd),
                columns = GridCells.Fixed(4)
            ) {
                items(pagingItems, viewModel)
            }
        }

    }
}

@Composable
private fun lazyPagingItems(
    images: GalleryImage?,
    viewModel: ReviewViewModel
) {
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
                painter = rememberAsyncImagePainter(images.uri),
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

@Composable
fun SelectedImages(viewmodel: ReviewViewModel) {
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
    viewmodel: ReviewViewModel
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
                            ReviewViewModel.CroppingImage(modifyingImage.id, view.croppedImage)
                    }
                    viewmodel.setCropStatus(ReviewViewModel.ImageCropStatus.WAITING)
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
                    text = if (isSelected) "이미지 수정하기" else "이미지 추가하기", modifier = Modifier
                        .clip(RoundedCornerShape(30.dp))
                        .padding(20.dp, 10.dp)
                        .background(
                            Color_Default_Backgounrd
                        )
                        .clickable {
                            if (isSelected) {
                                viewmodel.setCropStatus(ReviewViewModel.ImageCropStatus.MODIFYING)
                            } else {
                                if (viewmodel.selectedImages.size <= 4) {
                                    viewmodel.setCropStatus(ReviewViewModel.ImageCropStatus.CROPPING)
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
    appState: ApplicationState,
    scope: CoroutineScope,
    reviewViewModel: ReviewViewModel
) {
    var isDropdownMenuExpanded by remember {
        mutableStateOf(false)
    }

    Row(
        Modifier
            .fillMaxWidth()
            .padding(20.dp, 10.dp)
            .height(40.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = "취소", color = Color.White, modifier = Modifier.clickable {
            appState.navController.popBackStack()
        })
        Row(verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .weight(1f)
                .clickable {
                    isDropdownMenuExpanded = true
                }) {
            Text(
                text = reviewViewModel.currentLocation.value.first,
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
            Icon(
                painter = painterResource(id = R.drawable.ic_baseline_arrow_right_24),
                modifier = Modifier
                    .rotate(if (isDropdownMenuExpanded) 270f else 90f)
                    .size(32.dp),
                contentDescription = null,
                tint = Color.White
            )
        }
//        MaterialTheme(shapes = MaterialTheme.shapes.copy(medium = RoundedCornerShape(16.dp))) {
        DropdownMenu(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color_Default_Backgounrd)
                .border(
                    BorderStroke(1.dp, Color.White.copy(alpha = 0.8f))
                ),
            expanded = isDropdownMenuExpanded,
            onDismissRequest = { isDropdownMenuExpanded = false }) {
            reviewViewModel.directories.map {
                DropdownMenuItem(onClick = {
                    isDropdownMenuExpanded = false
                    reviewViewModel.setCurrentLocation(it)
                }) {
                    Text(
                        text = it.first,
                        color = Color.White,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
//            }
        }
        val location = reviewViewModel.directories
//        Text(
//            text = "현재위치",
//            color = Color.White,
//            fontSize = 18.sp,
//            textAlign = TextAlign.Center,
//            modifier = Modifier.weight(1f)
//        )
        val nothingSelected = reviewViewModel.selectedImages.isEmpty()
        Text(text = "확인",
            color = if (nothingSelected) Color_White_70 else Color.White,
            modifier = Modifier.clickable {
                if (nothingSelected) {
                    scope.launch {
                        appState.scaffoldState.showSnackbar("하나 이상의 사진을 추가해 주세요.")
                    }
                } else {
                    appState.navController.previousBackStackEntry
                        ?.savedStateHandle
                        ?.set(
                            "bitmap_images",
                            reviewViewModel.selectedImages.toList()
                        )
                    appState.navController.popBackStack()
                }

            })
    }
}

@Composable
@Preview
fun PreviewGalleryScreen() {
//    GalleryScreen()
}
