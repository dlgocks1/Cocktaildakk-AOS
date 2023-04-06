package com.compose.cocktaildakk_compose.ui.detail.components

import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.compose.cocktaildakk_compose.ui.detail.model.CroppingImage
import com.compose.cocktaildakk_compose.ui.utils.galleryPermissionCheck

@Composable
fun PictureUpload(
    galleryLauncher: ManagedActivityResultLauncher<String, Boolean>,
    navigateToGallery: () -> Unit,
    secondScreenResult: List<CroppingImage>?,
) {
    val picktureCount = secondScreenResult?.size ?: 0
    val context = LocalContext.current
    Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(5.dp)) {
        Text(text = "칵테일 사진을 업로드 해 주세요.", fontSize = 16.sp, color = Color.White)
        Text(text = "* 사진은 최대 5장까지 업로드 가능합니다.", fontSize = 12.sp, color = Color.White)
        Spacer(modifier = Modifier.height(5.dp))
        Box(
            modifier = Modifier
                .background(Color.Transparent)
                .border(1.dp, Color.White, RoundedCornerShape(30))
                .clickable {
                },
        ) {
            Text(
                modifier = Modifier
                    .padding(15.dp, 5.dp)
                    .clickable {
                        galleryPermissionCheck(
                            context = context,
                            launcher = galleryLauncher,
                            action = navigateToGallery,
                        )
                    },
                text = "사진 업로드 $picktureCount/5",
                color = Color.White,
            )
        }
        PictureContent(secondScreenResult = secondScreenResult)
    }
}