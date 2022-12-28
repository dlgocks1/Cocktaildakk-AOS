package com.compose.cocktaildakk_compose.ui.onboarding

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.compose.cocktaildakk_compose.*
import com.compose.cocktaildakk_compose.R
import com.compose.cocktaildakk_compose.ui.components.ImageWithBackground
import com.compose.cocktaildakk_compose.ui.theme.ScreenRoot.MAIN_GRAPH
import com.compose.cocktaildakk_compose.ui.theme.ScreenRoot.ONBOARD_GRAPH
import com.compose.cocktaildakk_compose.ui.theme.ScreenRoot.ONBOARD_START
import kotlinx.coroutines.launch

@Composable
fun OnboardStartScreen(
    navController: NavController = rememberNavController(),
    onboardViewModel: OnboardViewModel = hiltViewModel()
) {

    val scope = rememberCoroutineScope()
    val skipDialog = remember { mutableStateOf(false) }

    ImageWithBackground(
        modifier = Modifier
            .fillMaxSize()
            .blur(20.dp),
        backgroundDrawableResId = R.drawable.img_onboard_back,
        contentDescription = "Img Onboard Back"
    ) {
        Box {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
            ) {
                Column(
                    modifier = Modifier
                        .padding(40.dp)
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(30.dp, Alignment.CenterVertically)
                ) {
                    Spacer(modifier = Modifier.weight(1f))
                    Text(
                        text = SET_FAVOR_KEYWORD_TITLE_TEXT,
                        fontSize = 40.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(text = SET_FAVOR_KEYWORD_SUB_TEXT, fontSize = 22.sp)
                    Surface(
                        modifier = Modifier
                            .background(color = Color.Transparent)
                            .clickable {
                                navController.navigate("onboard_nickname")
                            },
                        color = Color.Transparent
                    ) {
                        Text(
                            text = "시작하기", modifier = Modifier
                                .border(
                                    brush = Brush.horizontalGradient(
                                        listOf(
                                            Color.Green,
                                            Color.Blue
                                        )
                                    ),
                                    width = 1.dp,
                                    shape = CircleShape
                                )
                                .padding(20.dp, 10.dp)
                        )
                    }
                    Text(
                        text = "건너뛰기", modifier = Modifier
                            .offset(x = 20.dp)
                            .clickable {
                                skipDialog.value = true
                            }
                    )
                    Spacer(modifier = Modifier.weight(2f))
                }
            }
            if (skipDialog.value) {
                SkipDialog(onDismiss = {
                    skipDialog.value = !skipDialog.value
                }, onClick = {
                    skipDialog.value = !skipDialog.value
                    scope.launch {
                        onboardViewModel.insertUserinfo {
                            navigateToMain(navController)
                        }
                    }
                })
            }
        }
    }
}

fun navigateToMain(navController: NavController) {
    navController.navigate(MAIN_GRAPH) {
        popUpTo(0)
    }
}

@Composable
fun SkipDialog(onClick: () -> Unit, onDismiss: () -> Unit) {

    Dialog(onDismissRequest = { onDismiss() }) {
        Card(
            elevation = 8.dp,
            shape = RoundedCornerShape(12.dp)
        ) {
            Column(
                modifier = Modifier.padding(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_baseline_info_24),
                    contentDescription = "Icon Info",
                    tint = Color.White,
                    modifier = Modifier
                        .padding(0.dp, 10.dp)
                        .size(24.dp)
                )
                Text(
                    text = SKIP_INFO,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    modifier = Modifier.padding(8.dp),
                    textAlign = TextAlign.Center
                )
                // Buttons
                Row(
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    TextButton(onClick = { onDismiss() }) {
                        Text(text = CANCEL_TEXT, color = Color.White)
                    }
                    Spacer(modifier = Modifier.width(4.dp))
                    TextButton(onClick = {
                        onClick()
                    }) {
                        Text(text = CONFIRM_TEXT, color = Color.White)
                    }
                }
            }
        }
    }

}

@Preview
@Composable
fun PreviewOnboardStartScreen() {
    OnboardStartScreen()
}

