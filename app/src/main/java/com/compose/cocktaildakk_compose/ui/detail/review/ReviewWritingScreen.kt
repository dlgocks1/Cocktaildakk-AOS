@file:OptIn(
    ExperimentalAnimationApi::class, ExperimentalFoundationApi::class,
    ExperimentalFoundationApi::class
)

package com.compose.cocktaildakk_compose.ui.detail.review

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.view.ViewTreeObserver
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.compose.cocktaildakk_compose.R
import com.compose.cocktaildakk_compose.domain.model.Cocktail
import com.compose.cocktaildakk_compose.ui.ApplicationState
import com.compose.cocktaildakk_compose.ui.detail.BlurBackImg
import com.compose.cocktaildakk_compose.ui.detail.ReviewViewModel
import com.compose.cocktaildakk_compose.ui.theme.Color_Default_Backgounrd
import com.compose.cocktaildakk_compose.ui.theme.Color_Default_Backgounrd_70
import com.compose.cocktaildakk_compose.ui.theme.Color_White_70
import com.compose.cocktaildakk_compose.ui.theme.ScreenRoot.GALLERY
import com.compose.cocktaildakk_compose.ui.utils.showSnackbar
import kotlinx.coroutines.launch

@Composable
fun ReviewWritingScreen(
    viewModel: ReviewViewModel = hiltViewModel(),
    appState: ApplicationState,
    idx: Int,
) {
    val userContents = viewModel.userContents

    val secondScreenResult = appState.navController.currentBackStackEntry
        ?.savedStateHandle
        ?.getLiveData<List<ReviewViewModel.CroppingImage>>("bitmap_images")
        ?.observeAsState()

    val scope = rememberCoroutineScope()

    val context = LocalContext.current
    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            appState.navController.navigate(GALLERY)
        } else {
            scope.launch {
                appState.showSnackbar("권한을 허가해 주세요.")
            }
        }
    }

    LaunchedEffect(Unit) {
        viewModel.getDetail(idx)
    }

    val bringIntoViewRequester = remember { BringIntoViewRequester() }
    val view = LocalView.current
    DisposableEffect(view) {
        val listener = ViewTreeObserver.OnGlobalLayoutListener {
            scope.launch { bringIntoViewRequester.bringIntoView() }
        }
        view.viewTreeObserver.addOnGlobalLayoutListener(listener)
        onDispose { view.viewTreeObserver.removeOnGlobalLayoutListener(listener) }
    }


    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
        ) {
            BlurBackImg(cocktail = Cocktail())
            TopBar("리뷰 작성하기") {
                appState.navController.popBackStack()
            }
        }
        Box(
            modifier = Modifier
                .padding(top = 50.dp)
                .fillMaxSize()
                .clip(RoundedCornerShape(topStart = 25.dp, topEnd = 25.dp))
                .height(17.dp)
                .background(color = Color_Default_Backgounrd),
        ) {
            Column(
                Modifier
                    .fillMaxSize()
                    .padding(20.dp)
                    .verticalScroll(rememberScrollState()),
            ) {
                StarRating(viewModel)
                Spacer(modifier = Modifier.height(30.dp))
                PicktureUpload(context, launcher, appState.navController, secondScreenResult)
                Spacer(modifier = Modifier.height(10.dp))
                Column {
                    Text(
                        text = "${userContents.value.text.length} / 150",
                        color = Color.White,
                        textAlign = TextAlign.End,
                        fontSize = 14.sp,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Column(
                        modifier = Modifier
                            .heightIn(min = 200.dp)
                            .border(1.dp, Color.White),
                    ) {
                        BasicTextField(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(20.dp)
                                .bringIntoViewRequester(bringIntoViewRequester),
                            value = userContents.value,
                            onValueChange = {
                                if (it.text.length <= 150) {
                                    userContents.value = it
                                }
                            },
                            cursorBrush = SolidColor(Color.White),
                            textStyle = TextStyle(fontSize = 14.sp, color = Color.White),
                            decorationBox = { innerTextField ->
                                if (userContents.value.text.isEmpty()) {
                                    Text(text = "칵테일에 대한 정보 입력", color = Color.Gray)
                                }
                                innerTextField()
                            })
                    }
                    Box(
                        Modifier
                            .fillMaxWidth(1f)
                            .padding(20.dp)
                            .clip(RoundedCornerShape(30))
                            .border(1.dp, Color.White, RoundedCornerShape(30))
                    ) {
                        Text(
                            text = "작성 완료",
                            color = Color.White,
                            fontSize = 16.sp,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(0.dp, 10.dp)
                                .clickable {
                                    val picktureCount = (secondScreenResult?.value?.size) ?: 0

                                    if (viewModel.rankScore.value == 0) {
                                        scope.launch {
                                            appState.scaffoldState.showSnackbar("별점을 입력해 주세요.")
                                        }
                                    }
                                    if (picktureCount == 0 || userContents.value.text
                                            .trim()
                                            .isEmpty()
                                    ) {
                                        scope.launch {
                                            appState.scaffoldState.showSnackbar("하나 이상의 사진과 내용을 입력해주세요.")
                                        }
                                    } else {
                                        // 파이어베이스에 리뷰 넣기
                                        viewModel.addReviewToFirebase(
                                            idx = idx,
                                            images = secondScreenResult!!.value!!,
                                            onSuccess = {
                                                appState.navController.popBackStack()
                                            },
                                            onFailed = {
                                                scope.launch {
                                                    appState.showSnackbar("리뷰 업로드에 실패했습니다.")
                                                }
                                            }
                                        )
                                    }
                                }
                        )
                    }
                }
            }

        }
        if (viewModel.isLoading.value) {
            AnimatedProgressBar(viewModel.loadingState.value)
        }
    }
}

@Composable
private fun AnimatedProgressBar(loadingState: Int) {

    Column(
        Modifier
            .fillMaxSize(1f)
            .background(Color_Default_Backgounrd_70)
            .clickable {
                // Do Nothing!
            },
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AnimatedContent(targetState = loadingState) {
            CircularProgressIndicator(
                modifier = Modifier.size(64.dp),
                progress = loadingState.toFloat() / 100f,
                color = Color.White
            )
        }
        Spacer(modifier = Modifier.height(20.dp))
        AnimatedContent(targetState = loadingState) {
            Text(text = "업로드 중...", fontSize = 16.sp, color = Color.White)
        }
    }
}

@Composable
private fun PicktureUpload(
    context: Context,
    launcher: ManagedActivityResultLauncher<String, Boolean>,
    navController: NavHostController,
    secondScreenResult: State<List<ReviewViewModel.CroppingImage>?>?
) {

    val picktureCount = (secondScreenResult?.value?.size) ?: 0

    Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(5.dp)) {
        Text(text = "칵테일 사진을 업로드 해 주세요.", fontSize = 16.sp, color = Color.White)
        Text(text = "* 사진은 최대 5장까지 업로드 가능합니다.", fontSize = 12.sp, color = Color.White)
        Spacer(modifier = Modifier.height(5.dp))
        Box(
            modifier = Modifier
                .background(Color.Transparent)
                .border(1.dp, Color.White, RoundedCornerShape(30))
                .clickable {
                },
        ) {
            Text(
                modifier = Modifier
                    .padding(15.dp, 5.dp)
                    .clickable {
                        permissionCheck(context, launcher) {
                            navController.navigate(GALLERY)
                        }
                    },
                text = "사진 업로드 $picktureCount/5",
                color = Color.White
            )
        }
        PicktureContent(secondScreenResult)
    }
}

fun permissionCheck(
    context: Context,
    launcher: ManagedActivityResultLauncher<String, Boolean>,
    action: () -> Unit
) {
    when (PackageManager.PERMISSION_GRANTED) {
        ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.READ_EXTERNAL_STORAGE
        ) -> {
            action()
        }
        else -> {
            launcher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
        }
    }
}

@Composable
fun PicktureContent(secondScreenResult: State<List<ReviewViewModel.CroppingImage>?>?) {
    Spacer(modifier = Modifier.height(5.dp))
    Row(
        modifier = Modifier.horizontalScroll(rememberScrollState()),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        secondScreenResult?.value?.let {
            it.mapIndexed { idx, croppedImage ->
                Box {
                    Image(
                        painter = rememberAsyncImagePainter(croppedImage.croppedBitmap),
                        contentDescription = null,
                        modifier = Modifier.size(90.dp),
                        contentScale = ContentScale.Crop
                    )
                    Text(
                        text = (idx + 1).toString(),
                        color = Color.White,
                        fontSize = 14.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .padding(3.dp)
                            .clip(CircleShape)
                            .size(24.dp)
                            .background(Color_Default_Backgounrd)
                            .align(Alignment.BottomEnd)
                    )
                }
            }
        }
    }
}

@Composable
private fun StarRating(viewModel: ReviewViewModel) {
    Column(
        modifier = Modifier
            .padding(top = 20.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(5.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(text = viewModel.cocktailDetail.value.krName, fontSize = 30.sp, color = Color.White)
        Text(text = viewModel.cocktailDetail.value.enName, fontSize = 20.sp, color = Color.White)
        Spacer(modifier = Modifier.height(15.dp))
        Text(
            text = "별을 클릭하여\n\'${viewModel.cocktailDetail.value.krName}\'에 대한 별점을 평가해 주세요.",
            fontSize = 16.sp,
            color = Color_White_70,
            textAlign = TextAlign.Center
        )
        Rank(viewModel)
    }
}

@Composable
private fun Rank(viewModel: ReviewViewModel) {
    Row {
        repeat(5) {
            RankIcon(it + 1, viewModel)
        }
    }
}

@Composable
private fun RankIcon(rank: Int, viewModel: ReviewViewModel) {
    Icon(
        painter = painterResource(id = R.drawable.ic_baseline_star_24),
        contentDescription = null,
        modifier = Modifier
            .size(42.dp)
            .clickable {
                viewModel.setRankScore(rank)
            },
        tint = if (viewModel.rankScore.value >= rank) Color.White else Color_White_70
    )
}
