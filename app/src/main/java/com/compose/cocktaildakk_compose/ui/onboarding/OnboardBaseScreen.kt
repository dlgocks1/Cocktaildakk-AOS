package com.compose.cocktaildakk_compose.ui.onboarding

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.compose.cocktaildakk_compose.R
import com.compose.cocktaildakk_compose.ui.components.ImageWithBackground
import com.compose.cocktaildakk_compose.ui.components.TagCheckbox
import com.compose.cocktaildakk_compose.ui.onboarding.OnboardViewModel.TagList
import kotlinx.coroutines.launch

@Composable
fun OnboardBaseScreen(
    navController: NavController = rememberNavController(),
    onboardViewModel: OnboardViewModel = hiltViewModel(),
    scaffoldState: ScaffoldState
) {

    val scope = rememberCoroutineScope()
    val checkedState = remember {
        mutableStateListOf(
            TagList(text = "럼"),
            TagList(text = "위스키"),
            TagList(text = "진"),
            TagList(text = "데킬라"),
            TagList(text = "브랜디"),
            TagList(text = "보드카"),
        )
    }

    val noBase = remember {
        mutableStateOf(false)
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
                    text = "어떤 기주를\n선호하시나요?",
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
            ) {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(3),
                    verticalArrangement = Arrangement.spacedBy(20.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    itemsIndexed(checkedState) { index, it ->
                        TagCheckbox(
                            isChecked = it.isSelected,
                            onCheckChanged = {
                                noBase.value = false
                                checkedState[index] =
                                    checkedState[index].copy(isSelected = !it.isSelected)
                            },
                            text = it.text, modifier = Modifier
                        )
                    }
                }
                Surface(
                    modifier = Modifier
                        .fillMaxWidth(0.6f)
                        .offset(y = 30.dp)
                        .align(Alignment.CenterHorizontally), color = Color.Transparent
                ) {
                    TagCheckbox(
                        isChecked = noBase.value,
                        onCheckChanged = {
                            noBase.value = !noBase.value
                            for (i in 0 until checkedState.size) {
                                checkedState[i] = checkedState[i].copy(isSelected = false)
                            }
                        },
                        text = "마셔본 적이 없어요",
                        modifier = Modifier
                    )
                }
            }

            Surface(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .background(color = Color.Transparent)
                    .clickable {
                        val selectedBase = mutableListOf<String>()
                        if (noBase.value) {
                            selectedBase.add("상관 없음")
                        } else {
                            checkedState.forEach {
                                if (it.isSelected) {
                                    selectedBase.add(it.text)
                                }
                            }
                        }
                        if (selectedBase.isNotEmpty()) {
                            onboardViewModel.base = selectedBase
                            navController.navigate("onboard_keyword")
                        } else {
                            scope.launch {
                                scaffoldState.snackbarHostState.showSnackbar(
                                    message = "기주를 선택해 주세요.",
                                )
                            }
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
            Spacer(modifier = Modifier.fillMaxHeight(0.2f))
        }

    }
}
