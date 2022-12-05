package com.compose.cocktaildakk_compose.ui.detail

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.compose.cocktaildakk_compose.R
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.compose.cocktaildakk_compose.COCKTAIL_COLOR
import com.compose.cocktaildakk_compose.domain.model.BookmarkIdx
import com.compose.cocktaildakk_compose.domain.model.Cocktail
import com.compose.cocktaildakk_compose.ui.bookmark.BookmarkViewModel
import com.compose.cocktaildakk_compose.ui.components.TagButton
import com.compose.cocktaildakk_compose.ui.theme.Color_Default_Backgounrd
import com.compose.cocktaildakk_compose.ui.theme.Color_Default_Backgounrd_70
import com.google.accompanist.flowlayout.FlowRow

@Composable
fun DetailScreen(
    navController: NavController = rememberNavController(),
    detailViewModel: DetailViewModel = hiltViewModel(),
    idx: Int = 0
) {
    val colorList = remember {
        COCKTAIL_COLOR.shuffled()
    }
    val cocktail = detailViewModel.cocktailDetail.value
    LaunchedEffect(Unit) {
        detailViewModel.getDetail(idx = idx)
    }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Color_Default_Backgounrd)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(state = rememberScrollState())
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
            ) {
                BlurBackImg(cocktail)
                Column(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp, 10.dp)
                    ) {
                        Text(
                            text = cocktail.krName,
                            color = Color.White,
                            fontSize = 28.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(text = cocktail.enName, color = Color.White, fontSize = 18.sp)
                    }
                    RoundedTop()
                }
                SubcomposeAsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(cocktail.listImgUrl)
                        .crossfade(true)
                        .build(),
                    loading = {
                    },
                    contentDescription = stringResource(R.string.main_rec),
                    contentScale = ContentScale.FillHeight,
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .height(250.dp)
                        .padding(end = 20.dp),
                    error = {
                    }
                )
            }

            CoktailInfo(
                cocktail
            )
            Spacer(
                modifier = Modifier
                    .height(5.dp)
                    .fillMaxWidth()
                    .background(color = Color(0x40ffffff))
            )
            CoktailRecipe(cocktail = cocktail, colorList = colorList)
        }

        Surface(
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(20.dp)
                .clickable { navController.popBackStack() }
                .background(Color.Transparent),
            color = Color.Transparent
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_baseline_arrow_back_ios_24),
                contentDescription = "Img Back",
                tint = Color.White
            )
        }
    }

}

@Composable
fun CoktailRecipe(cocktail: Cocktail, colorList: List<Long>) {

    Column(modifier = Modifier.padding(20.dp)) {
        Surface(modifier = Modifier.padding(20.dp), color = Color.Transparent) {
            Text(
                text = "레시피",
                fontSize = 18.sp,
                color = Color.White,
                modifier = Modifier
                    .clip(RoundedCornerShape(10.dp))
                    .border(1.dp, Color.White, RoundedCornerShape(10.dp))
                    .padding(15.dp, 3.dp)
            )
        }
        Row(
            modifier = Modifier
                .padding(20.dp)
                .height(IntrinsicSize.Min)
                .heightIn(min = 150.dp),
            verticalAlignment = CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .weight(6f),
                verticalArrangement = Arrangement.spacedBy(10.dp, CenterVertically),
                horizontalAlignment = Alignment.Start
            ) {
                Column(
                    modifier = Modifier,
                    verticalArrangement = Arrangement.spacedBy(5.dp),
                    horizontalAlignment = Alignment.Start
                ) {
                    cocktail.ingredient.split(',').mapIndexed { index, it ->
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Canvas(modifier = Modifier.size(30.dp)) {
                                val canvasWidth = size.width
                                val canvasHeight = size.height
                                drawCircle(
                                    radius = size.minDimension / 4,
                                    color = Color(colorList[index]),
                                    center = Offset(x = canvasWidth / 2, y = canvasHeight / 2)
                                )
                            }
                            Text(modifier = Modifier.offset(x = 10.dp), text = it.trim())
                        }
                    }
                }
            }
            Box(
                modifier = Modifier
                    .weight(4f)
//          .wrapContentHeight()
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    val weightList = cocktail.ingredient.split(',').map {
                        val num: String = it.replace("[^0-9]".toRegex(), "")
                        if (num.isBlank()) {
                            15
                        } else {
                            15.coerceAtLeast(num.toInt())
                        }
                    }
                    weightList.mapIndexed { index, it ->
                        Canvas(
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(it.toFloat()),
                        ) {
                            drawRect(
                                color = Color(colorList[index]),
                            )
                            drawLine(
                                start = Offset(x = 0f, y = size.height),
                                end = Offset(x = size.width, y = size.height),
                                color = Color_Default_Backgounrd,
                                strokeWidth = 15f
                            )
                        }
                    }
                }
                // 삼각형 2개
                Canvas(
                    modifier = Modifier
                        .fillMaxSize(),
                ) {
                    val trianglePath = Path().apply {
                        moveTo(x = 0f, y = size.height)
                        lineTo(x = size.width * 0.2f, y = size.height)
                        lineTo(x = 0f, y = 0f)
                    }
                    drawPath(
                        color = Color_Default_Backgounrd,
                        path = trianglePath
                    )
                }
                Canvas(
                    modifier = Modifier
                        .fillMaxSize(),
                ) {
                    val trianglePath = Path().apply {
                        moveTo(x = size.width, y = size.height)
                        lineTo(x = size.width * 0.8f, y = size.height)
                        lineTo(x = size.width, y = 0f)
                    }
                    drawPath(
                        color = Color_Default_Backgounrd,
                        path = trianglePath
                    )
                }
            }

        }
    }
}

@Composable
fun CoktailInfo(cocktail: Cocktail) {

    val bookmarkViewModel: BookmarkViewModel = hiltViewModel()
    val isBookmarked =
        bookmarkViewModel.bookmarkList.value.contains(BookmarkIdx(idx = cocktail.idx))

    Column(
        modifier = Modifier.padding(20.dp, 0.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
//    Row(verticalAlignment = Alignment.CenterVertically) {
//      Text(
//        text = "별점",
//        fontSize = 16.sp,
//        modifier = Modifier.width(60.dp),
//        fontWeight = FontWeight.Bold
//      )
//      for (i in 0..5) {
//        Icon(
//          painter = painterResource(id = R.drawable.ic_baseline_star_24),
//          contentDescription = null,
//          modifier = Modifier.size(16.dp)
//        )
//      }
//    }
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "도수",
                    fontSize = 18.sp,
                    modifier = Modifier.width(60.dp),
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "약 ${cocktail.level}도", fontSize = 16.sp,
                    modifier = Modifier.width(60.dp),
                )
            }
            Icon(
                painter = painterResource(
                    id = if (isBookmarked)
                        R.drawable.ic_baseline_bookmark_24 else
                        R.drawable.ic_outline_bookmark_border_24
                ), contentDescription = null, modifier = Modifier.clickable {
                    if (isBookmarked) {
                        bookmarkViewModel.deleteBookmark(cocktail.idx)
                    } else {
                        bookmarkViewModel.insertBookmark(cocktail.idx)
                    }
                }
            )
        }
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "섞는법",
                    fontSize = 18.sp,
                    modifier = Modifier.width(60.dp),
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "${cocktail.mix}", fontSize = 16.sp,
                    modifier = Modifier.fillMaxWidth(),
                )
            }
        }
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "기주",
                    fontSize = 18.sp,
                    modifier = Modifier.width(60.dp),
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "${cocktail.base}", fontSize = 16.sp,
                    modifier = Modifier.fillMaxWidth(),
                )
            }
        }
        Row() {
            Text(
                text = "키워드",
                fontSize = 18.sp,
                modifier = Modifier.width(60.dp),
                fontWeight = FontWeight.Bold
            )
            FlowRow(
                modifier = Modifier.fillMaxWidth(),
                crossAxisSpacing = 10.dp,
            ) {
                val tag = cocktail.keyword.split(',')
                tag.indices.forEach { i ->
                    TagButton(tag[i])
                    Spacer(modifier = Modifier.width(10.dp))
                }
            }
        }
        Column() {
            Text(
                text = "칵테일 설명",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = cocktail.explain
            )
        }
        Row() {
            Text(
                fontSize = 18.sp,
                text = "필요한 재료",
                modifier = Modifier.weight(0.3f),
                fontWeight = FontWeight.Bold
            )
            Column(
                modifier = Modifier.weight(0.7f),
                verticalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                cocktail.ingredient.split(',').map {
                    Text(text = it.trim())
                }
            }
        }
    }
    Spacer(modifier = Modifier.height(20.dp))

}

@Composable
private fun BlurBackImg(cocktail: Cocktail) {
//  Image(
//    painter = painterResource(id = R.drawable.img_main_dummy),
//    contentDescription = "Img Backgound",
//    modifier = Modifier
//      .fillMaxSize()
//      .blur(15.dp),
//    contentScale = ContentScale.Crop
//  )
    SubcomposeAsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(cocktail.imgUrl)
            .crossfade(true)
            .build(),
        loading = {
//      ListCircularProgressIndicator(fraction = 0.2f)
        },
        contentDescription = stringResource(R.string.main_rec),
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .fillMaxSize()
            .blur(15.dp),
        error = {
            Image(
                painter = painterResource(id = R.drawable.img_main_dummy),
                contentDescription = "Img Backgound",
                modifier = Modifier
                    .fillMaxSize()
                    .blur(15.dp),
                contentScale = ContentScale.Crop
            )
        }
    )

    Spacer(
        modifier = Modifier
            .fillMaxSize()
            .background(Color_Default_Backgounrd_70),
    )
}

@Composable
private fun RoundedTop() {
    Box(modifier = Modifier.height(20.dp)) {
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .border(
                    3.dp, Color.White, RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)
                )
                .height(20.dp)
        )
        Spacer(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .fillMaxWidth()
                .clip(
                    RoundedCornerShape(topStart = 25.dp, topEnd = 25.dp)
                )
                .height(17.dp)
                .background(color = Color_Default_Backgounrd)
        )
    }
}

