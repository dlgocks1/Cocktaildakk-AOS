package com.compose.cocktaildakk_compose.ui.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.compose.cocktaildakk_compose.R
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color

@Composable
fun DetailScreen(navController: NavController = rememberNavController()) {

	Box(
		modifier = Modifier
			.fillMaxWidth()
			.height(200.dp)
	) {
		Image(
			painter = painterResource(id = R.drawable.img_main_dummy),
			contentDescription = "Img Backgound",
			modifier = Modifier
				.fillMaxSize()
				.blur(15.dp)
		)
		Icon(
			painter = painterResource(id = R.drawable.ic_baseline_arrow_back_ios_24),
			contentDescription = "Img Back",
			modifier = Modifier
				.align(Alignment.TopStart)
				.padding(20.dp),
			tint = Color.White
		)
	}

}