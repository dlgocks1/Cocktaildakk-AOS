package com.compose.cocktaildakk_compose.ui.detail.components

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.compose.cocktaildakk_compose.R
import com.compose.cocktaildakk_compose.domain.model.GalleryImage
import com.compose.cocktaildakk_compose.ui.components.ListCircularProgressIndicator
import com.compose.cocktaildakk_compose.ui.detail.model.CroppingImage
import com.compose.cocktaildakk_compose.ui.theme.Color_Default_Backgounrd

@Composable
fun galleryItemContent(
    galleryImage: GalleryImage,
    selectedImages: List<CroppingImage>,
    setModifyingImage: (GalleryImage) -> Unit,
    removeSelectedImage: (Long) -> Unit,
) {
    val isSelecetd = selectedImages.find { it.id == galleryImage.id } != null

    Box {
        SubcomposeAsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(galleryImage.uri)
                .crossfade(true)
                .build(),
            loading = {
                ListCircularProgressIndicator(fraction = 0.2f)
            },
            contentDescription = stringResource(R.string.main_rec),
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .aspectRatio(1f)
                .padding(2.dp)
                .animateContentSize()
                .clickable {
                    setModifyingImage(galleryImage)
                },
            alpha = if (isSelecetd) 0.5f else 1f,
            error = {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_baseline_error_outline_24),
                        contentDescription = "Icon Error",
                        modifier = Modifier.size(16.dp),
                        tint = Color.White,
                    )
                    Spacer(modifier = Modifier.height(5.dp))
                    Text(
                        text = "지원하지 않는\n파일 형식입니다.",
                        fontSize = 8.sp,
                        textAlign = TextAlign.Center,
                    )
                }
            },
        )
        if (isSelecetd) {
            Icon(
                modifier = Modifier
                    .padding(8.dp)
                    .size(24.dp)
                    .clip(CircleShape)
                    .background(
                        Color_Default_Backgounrd,
                    )
                    .align(Alignment.TopEnd)
                    .clickable {
                        removeSelectedImage(galleryImage.id)
                    },
                painter = painterResource(id = R.drawable.ic_baseline_close_24),
                contentDescription = "이미지 취소",
                tint = Color.White,
            )
        }
    }
}