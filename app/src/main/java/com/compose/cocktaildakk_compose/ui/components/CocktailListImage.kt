package com.compose.cocktaildakk_compose.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.compose.cocktaildakk_compose.R
import com.compose.cocktaildakk_compose.domain.model.Cocktail
import com.compose.cocktaildakk_compose.ui.theme.Color_Default_Backgounrd

@Composable
fun CocktailListImage(
    cocktail: Cocktail
) {
    SubcomposeAsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(cocktail.listImgUrl)
            .crossfade(true)
            .build(),
        loading = {
            ListCircularProgressIndicator(fraction = 0.5f)
        },
        contentDescription = stringResource(R.string.main_rec),
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .fillMaxWidth(0.5f)
            .height(100.dp)
            .background(Color_Default_Backgounrd),
        alignment = Alignment.Center,
        error = {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_baseline_error_outline_24),
                    contentDescription = "Icon Error",
                    modifier = Modifier.size(24.dp),
                    tint = Color.White
                )
            }
        },
    )
}