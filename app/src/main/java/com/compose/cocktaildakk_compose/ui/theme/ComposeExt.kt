package com.compose.cocktaildakk_compose.ui.theme

import android.graphics.BitmapFactory
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import coil.compose.rememberAsyncImagePainter
import com.compose.cocktaildakk_compose.R
import com.compose.cocktaildakk_compose.domain.model.GalleryImage
import com.compose.cocktaildakk_compose.ui.detail.gallery.GalleryViewModel

@ExperimentalFoundationApi
fun LazyGridScope.items(
    lazyPagingItems: LazyPagingItems<GalleryImage>,
    viewModel: GalleryViewModel,
//    itemContent: @Composable LazyItemScope.(value: GalleryImage?) -> Unit
) {
    items(lazyPagingItems.itemCount) { index ->
        itemContent(lazyPagingItems[index], viewModel = viewModel)
    }
}

@Composable
fun itemContent(
    images: GalleryImage?,
    viewModel: GalleryViewModel
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

