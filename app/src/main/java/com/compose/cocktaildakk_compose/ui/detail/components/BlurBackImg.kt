package com.compose.cocktaildakk_compose.ui.detail.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.compose.cocktaildakk_compose.R
import com.compose.cocktaildakk_compose.ui.theme.Color_Default_Backgounrd_70

@Composable
fun BlurBackImg(imgUrl: String) {
    SubcomposeAsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(imgUrl)
            .crossfade(true)
            .build(),
        loading = { },
        contentDescription = stringResource(R.string.main_rec),
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .fillMaxSize()
            .blur(15.dp),
        error = {
            Image(
                painter = painterResource(id = R.drawable.img_main_dummy),
                contentDescription = "BlurBackImg",
                modifier = Modifier
                    .fillMaxSize()
                    .blur(15.dp),
                contentScale = ContentScale.Crop,
            )
        },
    )
    Spacer(
        modifier = Modifier
            .fillMaxSize()
            .background(Color_Default_Backgounrd_70),
    )
}