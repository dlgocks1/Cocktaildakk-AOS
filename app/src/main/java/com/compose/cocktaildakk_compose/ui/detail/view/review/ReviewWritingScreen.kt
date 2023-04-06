@file:OptIn(ExperimentalFoundationApi::class)

package com.compose.cocktaildakk_compose.ui.detail.view.review

import android.view.ViewTreeObserver
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.compose.cocktaildakk_compose.ui.detail.ReviewViewModel
import com.compose.cocktaildakk_compose.ui.detail.components.BlurBackImg
import com.compose.cocktaildakk_compose.ui.detail.components.ContentUpload
import com.compose.cocktaildakk_compose.ui.detail.components.ReviewUploadAnimatedProgressBar
import com.compose.cocktaildakk_compose.ui.detail.components.TopBar
import com.compose.cocktaildakk_compose.ui.detail.model.CroppingImage
import com.compose.cocktaildakk_compose.ui.domain.model.ApplicationState
import com.compose.cocktaildakk_compose.ui.theme.Color_Default_Backgounrd
import com.compose.cocktaildakk_compose.ui.theme.ScreenRoot.GALLERY
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
        ?.getLiveData<List<CroppingImage>>("bitmap_images")
        ?.observeAsState()
    val scope = rememberCoroutineScope()
    val bringIntoViewRequester = remember { BringIntoViewRequester() }
    val view = LocalView.current
    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission(),
    ) { isGranted: Boolean ->
        if (isGranted) {
            appState.navController.navigate(GALLERY)
        } else {
            appState.showSnackbar("권한을 허가해 주세요.")
        }
    }

    LaunchedEffect(Unit) {
        viewModel.getDetail(idx)
    }

    DisposableEffect(view) {
        val listener = ViewTreeObserver.OnGlobalLayoutListener {
            scope.launch { bringIntoViewRequester.bringIntoView() }
        }
        view.viewTreeObserver.addOnGlobalLayoutListener(listener)
        onDispose { view.viewTreeObserver.removeOnGlobalLayoutListener(listener) }
    }

    Box(
        modifier = Modifier.fillMaxSize(),
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp),
        ) {
            BlurBackImg("")
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
            ContentUpload(
                cocktailDetail = viewModel.cocktailDetail.value,
                rankScore = viewModel.rankScore.value,
                bringIntoViewRequester = bringIntoViewRequester,
                launcher = launcher,
                setRankScore = { viewModel.setRankScore(it) },
                navigateToGallery = {
                    appState.navController.navigate(GALLERY)
                },
                secondScreenResult = secondScreenResult?.value,
                userContents = userContents.value,
                updateUserContent = { userContents.value = it },
                showSnackbar = { appState.showSnackbar(it) },
                addReviewToFirebase = addReviewToFirebase@{
                    if (secondScreenResult?.value.isNullOrEmpty() || secondScreenResult!!.value == null) {
                        appState.showSnackbar("사진을 업로드해주세요.")
                        return@addReviewToFirebase
                    }
                    viewModel.addReviewToFirebase(
                        idx = idx,
                        images = secondScreenResult.value!!,
                        onSuccess = {
                            appState.navController.popBackStack()
                        },
                        onFailed = {
                            appState.showSnackbar("리뷰 업로드에 실패했습니다.")
                        },
                    )
                },
            )
        }
        if (viewModel.isLoading.value) {
            ReviewUploadAnimatedProgressBar(viewModel.loadingState.value)
        }
    }
}
