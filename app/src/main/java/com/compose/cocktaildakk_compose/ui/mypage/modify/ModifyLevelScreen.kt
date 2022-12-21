package com.compose.cocktaildakk_compose.ui.mypage.modify

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
fun ModifyLevelScreen(
    navController: NavController = rememberNavController(),
    mypageViewModel: MypageViewModel = hiltViewModel(),
) {

    var sliderPosition = remember { mutableStateOf(5f) }

    LaunchedEffect(mypageViewModel.userInfo.value) {
        sliderPosition.value = mypageViewModel.userInfo.value.level.toFloat()
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
            Column(
                modifier = Modifier
                    .fillMaxHeight(0.3f)
                    .padding(40.dp, 0.dp)
            ) {
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = SET_LEVEL_TEXT,
                    fontSize = 36.sp,
                    modifier = Modifier,
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(modifier = Modifier.height(50.dp))

            Column(
                modifier = Modifier
                    .weight(0.7f)
                    .padding(40.dp, 0.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                Text(text = INFO_LEVEL_TEXT, fontSize = 20.sp)
                Slider(
                    value = sliderPosition.value,
                    onValueChange = { sliderPosition.value = it },
                    valueRange = 5f..35f,
                    onValueChangeFinished = {
                        // viewModel.updateSelectedSliderValue(sliderPosition)
                    },
                    steps = 5,
                    colors = SliderDefaults.colors(
                        thumbColor = Color_Cyan,
                        activeTrackColor = Color_Cyan
                    )
                )
                Text(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    text = if (sliderPosition.value.toInt() <= 5) MIN_LEVEL_TEXT
                    else if (sliderPosition.value.toInt() >= 35) MAX_LEVEL_TEXT
                    else "${sliderPosition.value.toInt()} $LEVEL_UNIT_TEXT",
                    fontSize = 17.sp
                )
            }

            Surface(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .background(color = Color.Transparent)
                    .clickable {
                        mypageViewModel.updateUserInfo(mypageViewModel.userInfo.value.copy(level = sliderPosition.value.toInt())) {
                            navController.popBackStack()
                        }
                    },
                color = Color.Transparent
            ) {
                Text(
                    text = CONFIRM_TEXT,
                    modifier = Modifier
                        .border(
                            brush = Brush.horizontalGradient(listOf(Color.Green, Color.Blue)),
                            width = 1.dp,
                            shape = CircleShape
                        )
                        .padding(20.dp, 10.dp),
                )
            }
            Spacer(modifier = Modifier.fillMaxHeight(0.2f))
        }

    }
}


