package com.compose.cocktaildakk_compose.ui.onboarding

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.compose.cocktaildakk_compose.R
import com.compose.cocktaildakk_compose.ui.components.ImageWithBackground
import com.compose.cocktaildakk_compose.ui.theme.Color_Female
import com.compose.cocktaildakk_compose.ui.theme.Color_Male

@Composable
fun OnboardSexScreen(
    navController: NavController = rememberNavController(),
    onboardViewModel: OnboardViewModel = hiltViewModel()
) {

    val selectedSex = remember {
        mutableStateOf("")
    }
    ImageWithBackground(
        modifier = Modifier
            .fillMaxSize()
            .blur(20.dp),
        backgroundDrawableResId = R.drawable.img_onboard_back,
        contentDescription = "Img Onboard Back"
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxHeight(0.3f)
                    .padding(40.dp, 0.dp)
            ) {
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = "성별을\n알려 주세요.",
                    fontSize = 36.sp,
                    modifier = Modifier,
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(modifier = Modifier.height(50.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.7f)
                    .padding(50.dp, 30.dp),
                horizontalArrangement = Arrangement.spacedBy(50.dp, Alignment.CenterHorizontally),
            ) {
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .clickable {
                            selectedSex.value = "Male"
                        }, horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Crossfade(targetState = selectedSex.value == "Male") { isChecked ->
                        if (isChecked) {
                            Image(
                                painter = painterResource(
                                    id = R.drawable.img_onboard_male_selected
                                ),
                                contentDescription = "Img Gender Male Selected",
                                contentScale = ContentScale.FillHeight,
                                modifier = Modifier
                                    .height(150.dp)
                                    .width(80.dp)
                            )
                        } else {
                            Image(
                                painter = painterResource(
                                    id = R.drawable.img_onboard_male_unselected
                                ),
                                contentDescription = "Img Gender Male Unselected",
                                contentScale = ContentScale.FillHeight,
                                modifier = Modifier
                                    .height(150.dp)
                                    .width(80.dp)
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(20.dp))
                    Text(
                        text = "남성", fontSize = 20.sp,
                        color = if (selectedSex.value == "Male") Color_Male else Color.White
                    )
                }
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .clickable {
                            selectedSex.value = "Female"
                        }, horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Crossfade(targetState = selectedSex.value == "Female") { isChecked ->
                        if (isChecked) {
                            Image(
                                painter = painterResource(
                                    id = R.drawable.img_onboard_female_selected
                                ),
                                contentDescription = "Img Gender Female Selected",
                                contentScale = ContentScale.FillHeight,
                                modifier = Modifier
                                    .height(150.dp)
                                    .width(80.dp)
                            )
                        } else {
                            Image(
                                painter = painterResource(
                                    id = R.drawable.img_onboard_female_unselected
                                ),
                                contentDescription = "Img Gender Female Unselected",
                                contentScale = ContentScale.FillHeight,
                                modifier = Modifier
                                    .height(150.dp)
                                    .width(80.dp)
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(20.dp))
                    Text(
                        text = "여성", fontSize = 20.sp,
                        color = if (selectedSex.value == "Female") Color_Female else Color.White
                    )
                }
            }
            AnimatedVisibility(
                visible = selectedSex.value.isNotBlank(),
                enter = fadeIn(
                    // Overwrites the initial value of alpha to 0.4f for fade in, 0 by default
                    initialAlpha = 0.4f
                ),
                exit = fadeOut(
                    // Overwrites the default animation with tween
                    animationSpec = tween(durationMillis = 250)
                ), modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Surface(
                    modifier = Modifier
                        .background(color = Color.Transparent)
                        .clickable {
                            if (selectedSex.value.isNotBlank()) {
                                onboardViewModel.sex = selectedSex.value
                                navController.navigate("onboard_level")
                            } else {
                                return@clickable
                            }
                        },
                    color = Color.Transparent
                ) {
                    Text(
                        text = "다음",
                        modifier = Modifier
                            .border(
                                brush = Brush.horizontalGradient(listOf(Color.Green, Color.Blue)),
                                width = 1.dp,
                                shape = CircleShape
                            )
                            .padding(20.dp, 10.dp),
                    )
                }
            }
            Spacer(modifier = Modifier.weight(0.2f))
        }

    }
}


@Preview
@Composable
fun PreviewOnboardSexScreen() {
    OnboardSexScreen()
}