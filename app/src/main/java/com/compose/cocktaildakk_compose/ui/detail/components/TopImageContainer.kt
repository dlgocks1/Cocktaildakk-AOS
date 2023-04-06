package com.compose.cocktaildakk_compose.ui.detail.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.compose.cocktaildakk_compose.R
import com.compose.cocktaildakk_compose.domain.model.Cocktail

@Composable
fun DetailTopContainer(cocktail: Cocktail) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp),
    ) {
        BlurBackImg(cocktail.imgUrl)

        Column(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .fillMaxWidth(),
        ) {
            Column(
                modifier = Modifier.padding(20.dp, 10.dp),
            ) {
                Text(
                    text = cocktail.krName,
                    color = Color.White,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                )
                Text(text = cocktail.enName, color = Color.White, fontSize = 18.sp)
            }
            RoundedTop()
        }
        SubcomposeAsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(cocktail.listImgUrl)
                .crossfade(true)
                .build(),
            loading = {
            },
            contentDescription = stringResource(R.string.main_rec),
            contentScale = ContentScale.FillHeight,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .height(250.dp)
                .padding(end = 20.dp),
            error = {
            },
        )
    }
}