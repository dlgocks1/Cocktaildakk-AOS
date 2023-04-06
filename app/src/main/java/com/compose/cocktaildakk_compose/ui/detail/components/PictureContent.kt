package com.compose.cocktaildakk_compose.ui.detail.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.compose.cocktaildakk_compose.ui.detail.model.CroppingImage
import com.compose.cocktaildakk_compose.ui.theme.Color_Default_Backgounrd

@Composable
fun PictureContent(secondScreenResult: List<CroppingImage>?) {
    Spacer(modifier = Modifier.height(5.dp))
    Row(
        modifier = Modifier.horizontalScroll(rememberScrollState()),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        secondScreenResult?.let {
            it.mapIndexed { idx, croppedImage ->
                Box {
                    Image(
                        painter = rememberAsyncImagePainter(croppedImage.croppedBitmap),
                        contentDescription = null,
                        modifier = Modifier.size(90.dp),
                        contentScale = ContentScale.Crop,
                    )
                    Text(
                        text = (idx + 1).toString(),
                        color = Color.White,
                        fontSize = 14.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .padding(3.dp)
                            .clip(CircleShape)
                            .size(24.dp)
                            .background(Color_Default_Backgounrd)
                            .align(Alignment.BottomEnd),
                    )
                }
            }
        }
    }
}