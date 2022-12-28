package com.compose.cocktaildakk_compose.ui.theme

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.LazyGridScope
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
import com.compose.cocktaildakk_compose.ui.detail.ReviewViewModel
import java.io.ByteArrayOutputStream
import java.lang.Byte.decode
import java.util.*

@ExperimentalFoundationApi
fun LazyGridScope.items(
    lazyPagingItems: LazyPagingItems<GalleryImage>,
    viewModel: ReviewViewModel,
) {
    items(lazyPagingItems.itemCount) { index ->
        itemContent(lazyPagingItems[index], viewModel = viewModel)
    }
}

@Composable
fun itemContent(
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
                        .padding(8.dp)
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


fun stringToBitmap(byteString: String): Bitmap {
    val imageBytes = android.util.Base64.decode(byteString, android.util.Base64.DEFAULT);
    val image = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
    return image ?: throw Exception("직렬화 실패")
}

fun bitmapToString(bitmap: Bitmap): String {
    val byteArrayOutputStream = ByteArrayOutputStream()
    var result: String
    byteArrayOutputStream.use {
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
        result = android.util.Base64.encodeToString(
            byteArrayOutputStream.toByteArray(),
            android.util.Base64.DEFAULT
        )
    }
    return result.ifBlank { throw java.lang.IllegalArgumentException("비트맵 생성에 실패했습니다.") }
}
