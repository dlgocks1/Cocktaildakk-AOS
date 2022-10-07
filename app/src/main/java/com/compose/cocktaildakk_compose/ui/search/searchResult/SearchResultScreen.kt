@file:OptIn(ExperimentalMaterialApi::class)

package com.compose.cocktaildakk_compose.ui.search.searchResult

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.compose.cocktaildakk_compose.R
import com.compose.cocktaildakk_compose.ui.search.SearchViewModel
import com.compose.cocktaildakk_compose.ui.components.SearchButton
import com.compose.cocktaildakk_compose.ui.theme.Color_Cyan
import com.compose.cocktaildakk_compose.ui.theme.Color_Default_Backgounrd

@Composable
fun SearchResultScreen(
  navController: NavController = rememberNavController(),
  searchViewModel: SearchViewModel = hiltViewModel(),
) {

  LaunchedEffect(Unit) {
    searchViewModel.getCocktails()
  }

  Column(
    modifier = Modifier
      .fillMaxSize()
      .background(color = Color_Default_Backgounrd)
  ) {
    SearchButton(searchStr = searchViewModel.searchStrResult.value) {
      navController.navigate("search")
    }
    Text(
      text = "총 N개의 검색 결과",
      fontSize = 16.sp,
      modifier = Modifier.padding(start = 20.dp, bottom = 20.dp),
      color = Color.White,
      fontWeight = FontWeight.Bold
    )
    LazyColumn(modifier = Modifier.fillMaxSize()) {
      items(searchViewModel.searchList.value) { item ->
        SearchListItem(
          modifier = Modifier.clickable {
            navController.navigate("detail/${item.idx}")
          },
          krName = item.krName ?: "",
          enName = item.enName ?: ""
        )
      }
    }
  }
}

@Composable
fun SearchListItem(krName: String, enName: String, modifier: Modifier) {
  Row(
    modifier = modifier
      .fillMaxWidth()
      .height(120.dp)
      .padding(0.dp, 10.dp)

  ) {
    Surface(
      modifier = Modifier
        .width(100.dp)
        .fillMaxHeight()
        .padding(20.dp, 0.dp),
      color = Color.Transparent
    ) {
      Image(
        painter = painterResource(id = R.drawable.img_list_dummy),
        contentDescription = "Img List",
        modifier = Modifier
          .fillMaxWidth(0.5f)
          .fillMaxHeight(),
        alignment = Alignment.Center,
        contentScale = ContentScale.Crop
      )
    }
    Column(
      modifier = Modifier
        .weight(1f)
        .fillMaxHeight(),
      verticalArrangement = Arrangement.Center
    ) {
      Text(text = krName, fontSize = 18.sp, color = Color.White, fontWeight = FontWeight.Bold)
      Spacer(modifier = Modifier.height(5.dp))
      Text(text = enName, fontSize = 14.sp, color = Color(0x60ffffff))
      Spacer(modifier = Modifier.height(10.dp))
      Surface(
        modifier = Modifier
          .clip(RoundedCornerShape(10.dp))
          .border(BorderStroke(1.dp, Color_Cyan)), color = Color.Transparent
      ) {
        Text(
          text = "키워드",
          fontSize = 12.sp,
          color = Color.White,
          modifier = Modifier.padding(10.dp, 3.dp)
        )
      }
    }
    Surface(modifier = Modifier.padding(top = 20.dp, end = 20.dp), color = Color.Transparent) {
      Icon(
        modifier = Modifier.size(24.dp),
        painter = painterResource(R.drawable.ic_outline_bookmark_border_24),
        contentDescription = "Icon Bookmark",
        tint = Color.White
      )
    }

  }
}

@Preview
@Composable
fun PreviewSearchResult() {
  SearchResultScreen()
}

