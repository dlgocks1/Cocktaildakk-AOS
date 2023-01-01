package com.compose.cocktaildakk_compose.ui.mypage.modify

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.compose.cocktaildakk_compose.*
import com.compose.cocktaildakk_compose.R
import com.compose.cocktaildakk_compose.domain.model.UserInfo.Companion.MAX_NICKNAME_LENGTH
import com.compose.cocktaildakk_compose.domain.model.UserInfo.Companion.MIN_NICKNAME_LENGTH
import com.compose.cocktaildakk_compose.ui.components.ImageWithBackground
import com.compose.cocktaildakk_compose.ui.mypage.MypageViewModel
import com.compose.cocktaildakk_compose.ui.utils.CustomTextField
import kotlinx.coroutines.launch

@Composable
fun ModifyNicknameScreen(
    navController: NavController = rememberNavController(),
    mypageViewModel: MypageViewModel = hiltViewModel(),
    scaffoldState: ScaffoldState,
) {
    val scope = rememberCoroutineScope()
    val focusRequest = remember {
        FocusRequester()
    }
    var textFieldValue = remember {
        val initValue = ""
        val textFieldValue =
            TextFieldValue(
                text = initValue,
                selection = TextRange(initValue.length),
            )
        mutableStateOf(textFieldValue)
    }

    LaunchedEffect(Unit) {
        focusRequest.requestFocus()
    }
    LaunchedEffect(mypageViewModel.userInfo.value) {
        textFieldValue.value = TextFieldValue(
            text = mypageViewModel.userInfo.value.nickname,
            selection = TextRange(mypageViewModel.userInfo.value.nickname.length),
        )
    }
    ImageWithBackground(
        modifier = Modifier
            .fillMaxSize()
            .blur(20.dp),
        backgroundDrawableResId = R.drawable.img_onboard_back,
        contentDescription = "Img Onboard Back",
        alpha = 0.2f,
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
                },
        )
        Column(
            modifier = Modifier
                .fillMaxSize(),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxHeight(0.4f)
                    .padding(40.dp, 0.dp),
            ) {
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = SET_NICKNAME_TEXT,
                    fontSize = 36.sp,
                    modifier = Modifier,
                    fontWeight = FontWeight.Bold,
                )
            }
            Spacer(modifier = Modifier.height(50.dp))

            Column(
                modifier = Modifier
                    .weight(0.6f)
                    .padding(40.dp, 0.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp),
            ) {
                CustomTextField(
                    trailingIcon = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(40.dp)
                        .background(
                            color = Color.White,
                            shape = RoundedCornerShape(20.dp),
                        ),
                    focusRequest = focusRequest,
                    fontSize = 16.sp,
                    placeholderText = INPUT_NICKNAME_TEXT,
                    value = textFieldValue.value,
                    onvalueChanged = {
                        if (it.text.length <= 10) {
                            textFieldValue.value = it
                        }
                    },
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                    keyboardActions = KeyboardActions(onDone = {
                        navigateNext(
                            mypageViewModel = mypageViewModel,
                            textFieldValue = textFieldValue,
                            navController = navController,
                        ) {
                            scope.launch {
                                scaffoldState.snackbarHostState.showSnackbar("3글자 이상 10글자 이하로 입력해주세요.")
                            }
                        }
                    }),
                )
                Text(text = NICKNAME_INFO_TEXT, fontSize = 14.sp)
            }

            Surface(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .background(color = Color.Transparent)
                    .clickable {
                        navigateNext(
                            mypageViewModel = mypageViewModel,
                            textFieldValue = textFieldValue,
                            navController = navController,
                        ) {
                            scope.launch {
                                scaffoldState.snackbarHostState.showSnackbar("3글자 이상 10글자 이하로 입력해주세요.")
                            }
                        }
                    },
                color = Color.Transparent,
            ) {
                Text(
                    text = NEXT_TEXT,
                    modifier = Modifier
                        .border(
                            brush = Brush.horizontalGradient(listOf(Color.Green, Color.Blue)),
                            width = 1.dp,
                            shape = CircleShape,
                        )
                        .padding(20.dp, 10.dp),
                )
            }
            Spacer(modifier = Modifier.fillMaxHeight(0.2f))
        }
    }
}

private fun navigateNext(
    mypageViewModel: MypageViewModel,
    textFieldValue: MutableState<TextFieldValue>,
    navController: NavController,
    makeSnackbar: () -> Unit,
) {
    if (textFieldValue.value.text.length in MIN_NICKNAME_LENGTH..MAX_NICKNAME_LENGTH) {
        mypageViewModel.updateUserInfo(mypageViewModel.userInfo.value.copy(nickname = textFieldValue.value.text)) {
            navController.popBackStack()
        }
    } else {
        makeSnackbar()
    }
}
