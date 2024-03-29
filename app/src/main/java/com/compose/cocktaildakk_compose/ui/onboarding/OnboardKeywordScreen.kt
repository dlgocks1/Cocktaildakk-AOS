package com.compose.cocktaildakk_compose.ui.onboarding

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.compose.cocktaildakk_compose.INFO_FAVOR_KEYWORD_TEXT
import com.compose.cocktaildakk_compose.R
import com.compose.cocktaildakk_compose.SET_FAVOR_KEYWORD_TEXT
import com.compose.cocktaildakk_compose.START_TEXT
import com.compose.cocktaildakk_compose.ui.components.ImageWithBackground
import com.compose.cocktaildakk_compose.ui.components.TagCheckbox
import com.compose.cocktaildakk_compose.ui.onboarding.OnboardViewModel.TagList
import com.compose.cocktaildakk_compose.ui.theme.Color_LightGreen
import com.google.accompanist.flowlayout.FlowRow
import kotlinx.coroutines.launch

@Composable
fun OnboardKeywordScreen(
    navController: NavController = rememberNavController(),
    onboardViewModel: OnboardViewModel = hiltViewModel(),
    scaffoldState: ScaffoldState,
) {
    val scope = rememberCoroutineScope()

    val checkedState = remember {
        onboardViewModel.keywordTagList.value.map {
            TagList(text = it.tagName)
        }
    }.toMutableStateList()

    ImageWithBackground(
        modifier = Modifier
            .fillMaxSize()
            .blur(20.dp),
        backgroundDrawableResId = R.drawable.img_onboard_back,
        contentDescription = "Img Onboard Back",
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxHeight(0.3f)
                    .padding(40.dp, 0.dp),
            ) {
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = SET_FAVOR_KEYWORD_TEXT,
                    fontSize = 36.sp,
                    modifier = Modifier,
                    fontWeight = FontWeight.Bold,
                )
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = INFO_FAVOR_KEYWORD_TEXT,
                    fontSize = 16.sp,
                    modifier = Modifier,
                )
            }
            Spacer(modifier = Modifier.height(50.dp))

            LazyColumn(
                modifier = Modifier
                    .weight(0.7f)
                    .padding(40.dp, 0.dp),
            ) {
                item {
                    FlowRow(
                        modifier = Modifier.fillMaxWidth(),
                        crossAxisSpacing = 10.dp,
                    ) {
                        for (i in 0 until checkedState.size) {
                            TagCheckbox(
                                isChecked = checkedState[i].isSelected,
                                onCheckChanged = {
                                    checkedState[i] =
                                        checkedState[i].copy(isSelected = !checkedState[i].isSelected)
                                },
                                text = checkedState[i].text,
                                modifier = Modifier,
                            )
                        }
                    }
                }
            }

            Surface(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .background(color = Color.Transparent)
                    .offset(y = 20.dp)
                    .clickable {
                        val selectedKeyword = mutableListOf<String>()
                        checkedState.forEach {
                            if (it.isSelected) selectedKeyword.add(it.text)
                        }
                        if (selectedKeyword.size <= 2) {
                            scope.launch {
                                scaffoldState.snackbarHostState.showSnackbar("3개 이상을 선택해 주세요.")
                            }
                        } else {
                            onboardViewModel.keyword = selectedKeyword
                            scope.launch {
                                onboardViewModel.insertUserinfo {
                                    navigateToMain(navController)
                                }
                            }
                        }
                    },
                color = Color.Transparent,
            ) {
                Text(
                    text = START_TEXT,
                    modifier = Modifier
                        .border(
                            color = Color_LightGreen,
                            width = 1.dp,
                            shape = CircleShape,
                        )
                        .clip(CircleShape)
                        .background(color = Color_LightGreen)
                        .padding(20.dp, 10.dp),
                    color = Color.Black,
                )
            }
            Spacer(modifier = Modifier.fillMaxHeight(0.2f))
        }
    }
}
