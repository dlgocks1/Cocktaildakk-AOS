package com.compose.cocktaildakk_compose.ui.detail.view.gallery

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import com.compose.cocktaildakk_compose.ui.detail.ReviewViewModel
import com.compose.cocktaildakk_compose.ui.detail.components.CustomImageCropView
import com.compose.cocktaildakk_compose.ui.detail.components.GalleryTopBar
import com.compose.cocktaildakk_compose.ui.detail.components.SelectedImages
import com.compose.cocktaildakk_compose.ui.detail.components.galleryItemContent
import com.compose.cocktaildakk_compose.ui.domain.model.ApplicationState
import com.compose.cocktaildakk_compose.ui.theme.Color_Default_Backgounrd
import com.compose.cocktaildakk_compose.ui.utils.getCroppedImageFromBackStack

@Composable
fun GalleryScreen(
    appState: ApplicationState,
    viewModel: ReviewViewModel = hiltViewModel(),
) {
    val pagingItems = viewModel.customGalleryPhotoList.collectAsLazyPagingItems()

    LaunchedEffect(viewModel.currentDirectory.value) {
        viewModel.getGalleryPagingImages()
    }

    LaunchedEffect(Unit) {
        val secondScreenResult =
            getCroppedImageFromBackStack(appState.navController.previousBackStackEntry)
        viewModel.addCropedImage(secondScreenResult)
        viewModel.getDirectory()
    }

    Column(modifier = Modifier.background(Color_Default_Backgounrd)) {
        GalleryTopBar(
            confirmCropImages = {
                appState.navController.previousBackStackEntry
                    ?.savedStateHandle
                    ?.set(
                        "bitmap_images",
                        viewModel.selectedImages.toList(),
                    )
                appState.navController.popBackStack()
            },
            showSnackbar = { message ->
                appState.showSnackbar(message)
            },
            currentDirectory = viewModel.currentDirectory.value,
            selectedImages = viewModel.selectedImages,
            directories = viewModel.directories,
            setCurrentDirectory = { directory ->
                viewModel.setCurrentDirectory(directory)
            },
            popBackStack = {
                appState.navController.popBackStack()
            },
        )

        SelectedImages(
            selectedImages = viewModel.selectedImages,
            removeSelectedImage = { id ->
                viewModel.removeSelectedImage(id)
            }
        )

        CustomImageCropView(
            showSnackbar = { message ->
                appState.showSnackbar(message)
            },
            modifyingImage = viewModel.modifyingImage.value,
            selectedImages = viewModel.selectedImages,
            selecetedStatus = viewModel.cropStatus.value,
            addSelectedImage = { id, bitmap ->
                viewModel.addSelectedImage(id, bitmap)
            },
            setCropStatus = { status ->
                viewModel.setCropStatus(status)
            },
            setSelectImages = { index, croppingImage ->
                viewModel.selectedImages[index] = croppingImage
            }
        )

        if (pagingItems.itemCount == 0) {
            Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.Center) {
                Text(
                    text = "이미지가 존재하지 않습니다.",
                    fontSize = 19.sp,
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth(),
                )
            }
        } else {
            LazyVerticalGrid(
                modifier = Modifier
                    .weight(1f)
                    .background(Color_Default_Backgounrd),
                columns = GridCells.Fixed(4),
            ) {
                items(pagingItems.itemCount) { index ->
                    pagingItems[index]?.let { galleryImage ->
                        galleryItemContent(
                            galleryImage = galleryImage,
                            setModifyingImage = { image ->
                                viewModel.setModifyingImage(image)
                            },
                            selectedImages = viewModel.selectedImages,
                            removeSelectedImage = { id ->
                                viewModel.removeSelectedImage(id)
                            },
                        )
                    }
                }
            }
        }
    }
}

