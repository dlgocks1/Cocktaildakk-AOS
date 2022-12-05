package com.compose.cocktaildakk_compose.ui.mypage.modify

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.compose.cocktaildakk_compose.*
import com.compose.cocktaildakk_compose.R
import com.compose.cocktaildakk_compose.ui.components.ImageWithBackground
import com.compose.cocktaildakk_compose.ui.mypage.MypageViewModel
import com.compose.cocktaildakk_compose.ui.theme.Color_Cyan

@Composable
fun ModifyCocktailWeightScreen(
    navController: NavController = rememberNavController(),
    mypageViewModel: MypageViewModel = hiltViewModel(),
) {

    var levelSliderPosition = remember { mutableStateOf(2f) }
    var baseSliderPosition = remember { mutableStateOf(2f) }
    var keywordsSliderPosition = remember { mutableStateOf(2f) }

    LaunchedEffect(mypageViewModel.cocktailWeight.value) {
        levelSliderPosition.value = (mypageViewModel.cocktailWeight.value.leveldWeight).toFloat()
        baseSliderPosition.value = (mypageViewModel.cocktailWeight.value.baseWeight).toFloat()
        keywordsSliderPosition.value =
            (mypageViewModel.cocktailWeight.value.keywordWeight).toFloat()
    }

    ImageWithBackground(
        modifier = Modifier
            .fillMaxSize()
            .blur(20.dp),
        backgroundDrawableResId = R.drawable.img_onboard_back,
        contentDescription = "Img Onboard Back", alpha = 0.2f
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_baseline_close_24),
            contentDescription = "Icon Close",
            tint = Color.White,
            modifier = Modifier
                .padding(30.dp)
                .size(24.dp)
                .clickable {
                    navController.popBackStack()
                }
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Spacer(modifier = Modifier.height(80.dp))
            Column(
                modifier = Modifier
                    .padding(40.dp, 0.dp),
            ) {
                Text(
                    text = "가중치를\n선택해 주세요.",
                    fontSize = 36.sp,
                    modifier = Modifier,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(5.dp))
                Text(text = "가중치를 기반으로 칵테일을 추천해 드려요.", fontSize = 14.sp)
            }
            Spacer(modifier = Modifier.height(50.dp))

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                Column(
                    modifier = Modifier
                        .padding(40.dp, 0.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Text(text = "도수 중요도", fontSize = 20.sp)
                    Slider(
                        value = levelSliderPosition.value,
                        onValueChange = { levelSliderPosition.value = it },
                        valueRange = 0f..4f,
                        onValueChangeFinished = {
                            // viewModel.updateSelectedSliderValue(sliderPosition)
                        },
                        steps = 3,
                        colors = SliderDefaults.colors(
                            thumbColor = Color_Cyan,
                            activeTrackColor = Color_Cyan
                        )
                    )
                    Text(
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        text = when (levelSliderPosition.value.toInt()) {
                            0 -> WEIGHT_NO_MATTER
                            1 -> WEIGHT_NOT_IMPORTANT
                            2 -> WEIGHT_NORMAL
                            3 -> WEIGHT_IMPORTANT
                            4 -> WEIGHT_HIGHLY_IMPORTANT
                            else -> "보통"
                        },
                        fontSize = 17.sp
                    )
                }

                Column(
                    modifier = Modifier
                        .padding(40.dp, 0.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Text(text = "기주 중요도", fontSize = 20.sp)
                    Slider(
                        value = baseSliderPosition.value,
                        onValueChange = { baseSliderPosition.value = it },
                        valueRange = 0f..4f,
                        onValueChangeFinished = {
                            // viewModel.updateSelectedSliderValue(sliderPosition)
                        },
                        steps = 3,
                        colors = SliderDefaults.colors(
                            thumbColor = Color_Cyan,
                            activeTrackColor = Color_Cyan
                        )
                    )
                    Text(
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        text = when (baseSliderPosition.value.toInt()) {
                            0 -> "상관 없음"
                            1 -> "중요하지 않음"
                            2 -> "보통"
                            3 -> "중요"
                            4 -> "매우 중요"
                            else -> "보통"
                        },
                        fontSize = 17.sp
                    )
                }
                Column(
                    modifier = Modifier
                        .padding(40.dp, 0.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Text(text = "키워드 중요도", fontSize = 20.sp)
                    Slider(
                        value = keywordsSliderPosition.value,
                        onValueChange = { keywordsSliderPosition.value = it },
                        valueRange = 0f..4f,
                        onValueChangeFinished = {
                            // viewModel.updateSelectedSliderValue(sliderPosition)
                        },
                        steps = 3,
                        colors = SliderDefaults.colors(
                            thumbColor = Color_Cyan,
                            activeTrackColor = Color_Cyan
                        )
                    )
                    Text(
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        text = when (keywordsSliderPosition.value.toInt()) {
                            0 -> "상관 없음"
                            1 -> "중요하지 않음"
                            2 -> "보통"
                            3 -> "중요"
                            4 -> "매우 중요"
                            else -> "보통"
                        },
                        fontSize = 17.sp
                    )
                }
                Spacer(modifier = Modifier.height(40.dp))
                Surface(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .background(color = Color.Transparent)
                        .clickable {
                            mypageViewModel.updateWeight(
                                levelWeight = levelSliderPosition.value.toInt(),
                                baseWeight = baseSliderPosition.value.toInt(),
                                keywordWeight = keywordsSliderPosition.value.toInt()
                            )
                            navController.popBackStack()
                        },
                    color = Color.Transparent
                ) {
                    Text(
                        text = "확인",
                        modifier = Modifier
                            .border(
                                brush = Brush.horizontalGradient(listOf(Color.Green, Color.Blue)),
                                width = 1.dp,
                                shape = CircleShape
                            )
                            .padding(20.dp, 10.dp),
                    )
                }
                Spacer(modifier = Modifier.height(40.dp))
            }
        }
    }

}


