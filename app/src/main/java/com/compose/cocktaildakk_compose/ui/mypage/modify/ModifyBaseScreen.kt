package com.compose.cocktaildakk_compose.ui.mypage.modify

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
import com.compose.cocktaildakk_compose.ui.components.TagCheckbox
import com.compose.cocktaildakk_compose.ui.mypage.MypageViewModel
import com.compose.cocktaildakk_compose.ui.onboarding.OnboardViewModel.TagList
import kotlinx.coroutines.launch

@Composable
fun ModifyBaseScreen(
    navController: NavController = rememberNavController(),
    mypageViewModel: MypageViewModel = hiltViewModel(),
    scaffoldState: ScaffoldState
) {

    val scope = rememberCoroutineScope()
    val checkedState = remember {
        mutableStateListOf(
            TagList(text = RUM),
            TagList(text = WHISKEY),
            TagList(text = JIN),
            TagList(text = TEQUILA),
            TagList(text = BRANDY),
            TagList(text = VODCA),
        )
    }

    val noBase = remember {
        mutableStateOf(false)
    }

    LaunchedEffect(mypageViewModel.userInfo.value) {
        for (i in checkedState.indices) {
            checkedState[i] = checkedState[i].copy(
                isSelected = mypageViewModel.userInfo.value.base.contains(
                    checkedState[i].text
                )
            )
        }
        noBase.value = mypageViewModel.userInfo.value.base.contains(
            NO_MATTER
        )
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
                    text = SELECT_BASE_TEXT,
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
                        text = NO_MATTER,
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
                            selectedBase.add(NO_MATTER)
                        } else {
                            checkedState.forEach {
                                if (it.isSelected) {
                                    selectedBase.add(it.text)
                                }
                            }
                        }
                        if (selectedBase.isNotEmpty()) {
                            mypageViewModel.updateUserInfo(mypageViewModel.userInfo.value.copy(base = selectedBase)) {
                                navController.popBackStack()
                            }
                        } else {
                            scope.launch {
                                scaffoldState.snackbarHostState.showSnackbar(
                                    message = SELECTE_BASE_TEXT,
                                )
                            }
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
