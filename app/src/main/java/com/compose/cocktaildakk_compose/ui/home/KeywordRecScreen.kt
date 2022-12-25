@file:OptIn(ExperimentalPagerApi::class)

package com.compose.cocktaildakk_compose.ui.home

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.compose.cocktaildakk_compose.*
import com.compose.cocktaildakk_compose.R
import com.compose.cocktaildakk_compose.SingletonObject.VISIBLE_SEARCH_STR
import com.compose.cocktaildakk_compose.domain.model.Cocktail
import com.compose.cocktaildakk_compose.ui.ApplicationState
import com.compose.cocktaildakk_compose.ui.Screen
import com.compose.cocktaildakk_compose.ui.components.CocktailListImage
import com.compose.cocktaildakk_compose.ui.components.ListCircularProgressIndicator
import com.compose.cocktaildakk_compose.ui.theme.Color_Cyan
import com.compose.cocktaildakk_compose.ui.theme.Color_Default_Backgounrd_70
import com.compose.cocktaildakk_compose.ui.theme.ScreenRoot
import com.compose.cocktaildakk_compose.ui.theme.ScreenRoot.DETAIL
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager


@Composable
fun KeywordRecScreen(
    appState: ApplicationState,
    homeViewModel: HomeViewModel = hiltViewModel()
) {
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(state = scrollState)
    ) {
        Text(
            text = RANDOM_COCKTAIL,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(20.dp, 20.dp)
        )
        TodayRecTable(
            navController = appState.navController,
            randomRecList = homeViewModel.randomRecList.value
        )
        Text(
            text = INFO_REC_COCKTAIL_TEXT,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(20.dp, 20.dp)
        )
        KeywordListTable(
            appState = appState,
            cocktailList = homeViewModel.baseTagRecList.value,
            tagName = homeViewModel.randomBaseTag
        )
        KeywordListTable(
            appState = appState,
            cocktailList = homeViewModel.keywordRecList.value,
            tagName = homeViewModel.randomKeywordTag.value,
        )
    }
}


@Composable
fun TodayRecTable(navController: NavController, randomRecList: List<Cocktail>) {
    HorizontalPager(
        count = randomRecList.size,
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 230.dp, max = 230.dp),
    ) { item ->
        Box {
//      Image(
//        modifier = Modifier
//          .fillMaxSize()
//          .clickable {
//            navController.navigate("detail/${randomRecList[item].idx}")
//          },
//        painter = painterResource(id = R.drawable.img_main_dummy),
//        contentDescription = "Today Rec Img",
//        contentScale = ContentScale.Crop
//      )
            SubcomposeAsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(randomRecList[item].imgUrl)
                    .crossfade(true)
                    .build(),
                loading = {
                    ListCircularProgressIndicator(fraction = 0.2f)
                },
                contentDescription = "Random Rec Img",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
                    .height(190.dp)
                    .clickable {
                        navController.navigate(DETAIL.format(randomRecList[item].idx))
//                        navController.navigate("detail/${randomRecList[item].idx}")
                    },
                error = {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_baseline_error_outline_24),
                            contentDescription = "Icon Error",
                            modifier = Modifier.size(28.dp),
                            tint = Color.White
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(text = CHECK_INTERNET_TEXT)
                    }
                }
            )
            Spacer(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        brush = Brush.horizontalGradient(
                            listOf(
                                Color.Transparent,
                                Color_Default_Backgounrd_70
                            )
                        )
                    ),
            )
            Column(
                modifier = Modifier
                    .padding(30.dp)
                    .align(Alignment.TopEnd),
                verticalArrangement = Arrangement.spacedBy(2.dp),
                horizontalAlignment = Alignment.End
            ) {
                Text(
                    text = randomRecList[item].krName,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(text = randomRecList[item].enName, fontSize = 16.sp)
                Spacer(modifier = Modifier.height(10.dp))
                randomRecList[item].keyword.split(',').take(4).map {
                    Text(text = "#$it", fontSize = 13.sp)
                }
            }
            Surface(
                modifier = Modifier
                    .padding(10.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(color = Color(0x30ffffff))
                    .align(Alignment.BottomEnd),
                color = Color.Transparent
            ) {
                Text(
                    text = "${item + 1} / 5",
                    color = Color.White,
                    fontSize = 12.sp,
                    modifier = Modifier
                        .padding(15.dp, 3.dp)
                        .background(Color.Transparent)
                )
            }
        }
    }
}

@Composable
fun KeywordListTable(
    appState: ApplicationState,
    cocktailList: List<Cocktail>,
    tagName: String,
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 20.dp, end = 20.dp, bottom = 30.dp),
        color = Color.Transparent,
        shape = RoundedCornerShape(15.dp),
        border = BorderStroke(1.dp, Color_Cyan)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 20.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp, 20.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "#$tagName $INFO_TAG_COCKTAIL_TEXT", fontSize = 16.sp,
                )
                Row(modifier = Modifier.clickable {
                    VISIBLE_SEARCH_STR.value = tagName
                    appState.navController.navigate(ScreenRoot.SEARCH_RESULT) {
                        popUpTo(Screen.Home.route) {
                            inclusive = true
                        }
                    }
                }) {
                    Text(text = MORE_INFO_TEXT, fontSize = 12.sp)
                    Icon(
                        painter = painterResource(id = R.drawable.ic_baseline_arrow_right_24),
                        contentDescription = "More Info Btn",
                        modifier = Modifier.size(12.dp)
                    )
                }
            }
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterHorizontally)
            ) {
                items(cocktailList) { item ->
                    Column(
                        modifier = Modifier
                            .width(100.dp)
                            .clickable {
                                appState.navController.navigate(DETAIL.format(item.idx))
                            },
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        CocktailListImage(item)
                        Text(
                            text = item.krName,
                            fontSize = 15.sp,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier.padding(5.dp, 0.dp)
                        )
                        Text(
                            text = item.enName,
                            fontSize = 10.sp,
                            color = Color(0xff5E5E5E),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.width(20.dp))

        }
    }
}
