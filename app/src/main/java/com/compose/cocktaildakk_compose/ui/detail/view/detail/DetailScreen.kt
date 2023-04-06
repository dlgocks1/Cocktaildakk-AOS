package com.compose.cocktaildakk_compose.ui.detail.view.detail

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.compose.cocktaildakk_compose.*
import com.compose.cocktaildakk_compose.R
import com.compose.cocktaildakk_compose.ui.components.HorizontalLineSpacer
import com.compose.cocktaildakk_compose.ui.detail.DetailViewModel
import com.compose.cocktaildakk_compose.ui.detail.components.*
import com.compose.cocktaildakk_compose.ui.theme.Color_Default_Backgounrd
import com.compose.cocktaildakk_compose.ui.theme.ScreenRoot.DETAIL_REVIEW

@Composable
fun DetailScreen(
    navController: NavController = rememberNavController(),
    detailViewModel: DetailViewModel = hiltViewModel(),
    idx: Int = 0,
) {
    val colorList = remember {
        COCKTAIL_COLOR.shuffled()
    }
    val cocktail = detailViewModel.cocktailDetail.value

    LaunchedEffect(Unit) {
        detailViewModel.getReview(idx = idx)
        detailViewModel.getDetail(idx = idx)
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Color_Default_Backgounrd),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(state = rememberScrollState()),
        ) {
            DetailTopContainer(cocktail = cocktail)
            CoktailInfo(cocktail = cocktail)
            HorizontalLineSpacer()
            CoktailRecipe(cocktail = cocktail, colorList = colorList)
            HorizontalLineSpacer()
            UserReviews(
                idx = idx,
                navigateToReview = { idx ->
                    navController.navigate(DETAIL_REVIEW.format(idx))
                },
                reviewContents = detailViewModel.reviewContents
            )
        }

        // Back Icon
        Surface(
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(20.dp)
                .clickable { navController.popBackStack() }
                .background(Color.Transparent),
            color = Color.Transparent,
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_baseline_arrow_back_ios_24),
                contentDescription = "Img Back",
                tint = Color.White,
            )
        }
    }
}

