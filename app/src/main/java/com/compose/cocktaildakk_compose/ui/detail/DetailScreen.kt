package com.compose.cocktaildakk_compose.ui.detail

import android.widget.Space
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.ScrollableState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.compose.cocktaildakk_compose.R
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.compose.cocktaildakk_compose.ui.components.TagButton
import com.compose.cocktaildakk_compose.ui.theme.Color_Cyan
import com.compose.cocktaildakk_compose.ui.theme.Color_Default_Backgounrd
import com.compose.cocktaildakk_compose.ui.theme.Color_Default_Backgounrd_70
import com.google.accompanist.flowlayout.FlowRow

@Composable
fun DetailScreen(navController: NavController = rememberNavController()) {

  Box(
    modifier = Modifier
      .fillMaxWidth()
      .background(color = Color_Default_Backgounrd)
  ) {

    Column(modifier = Modifier.fillMaxSize()) {
      Box(
        modifier = Modifier
          .fillMaxWidth()
          .height(250.dp)
      ) {
        BlurBackImg()
        Column(
          modifier = Modifier
            .align(Alignment.BottomStart)
            .fillMaxWidth()
        ) {
          Column(
            modifier = Modifier.padding(20.dp, 10.dp)
          ) {
            Text(
              text = "한국 이름",
              color = Color.White,
              fontSize = 28.sp,
              fontWeight = FontWeight.Bold
            )
            Text(text = "영어 이름", color = Color.White, fontSize = 18.sp)
          }
          RoundedTop()
        }
        Image(
          painter = painterResource(id = R.drawable.img_list_dummy),
          contentDescription = "Img Cocktail",
          modifier = Modifier
            .align(Alignment.BottomEnd)
            .height(250.dp)
            .padding(end = 20.dp),
          contentScale = ContentScale.FillHeight
        )
      }

      CoktailInfo()
      Spacer(
        modifier = Modifier
          .height(5.dp)
          .fillMaxWidth()
          .background(color = Color(0x40ffffff))
      )
      CoktailRecipe()
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
fun CoktailRecipe() {
  Row(modifier = Modifier.padding(20.dp)) {
    Column(modifier = Modifier.padding(10.dp)) {
      Text(text = "레시피")
      Text(text = "재료1")
      Text(text = "재료2")
    }
  }
}

@Composable
fun CoktailInfo() {
  Column(
    modifier = Modifier.padding(20.dp, 0.dp),
    verticalArrangement = Arrangement.spacedBy(20.dp)
  ) {
    Row(verticalAlignment = Alignment.CenterVertically) {
      Text(
        text = "별점",
        fontSize = 16.sp,
        modifier = Modifier.width(60.dp),
        fontWeight = FontWeight.Bold
      )
      for (i in 0..5) {
        Icon(
          painter = painterResource(id = R.drawable.ic_baseline_star_24),
          contentDescription = null,
          modifier = Modifier.size(16.dp)
        )
      }
    }
    Row {
      Text(
        text = "도수",
        fontSize = 16.sp,
        modifier = Modifier.width(60.dp),
        fontWeight = FontWeight.Bold
      )
      Text(
        text = "약20도", fontSize = 16.sp,
        modifier = Modifier.width(60.dp),
      )
    }
    Row() {
      Text(
        text = "키워드",
        fontSize = 16.sp,
        modifier = Modifier.width(60.dp),
        fontWeight = FontWeight.Bold
      )
      FlowRow(
        modifier = Modifier.fillMaxWidth(),
        crossAxisSpacing = 10.dp
      ) {
        for (i in 0..3) {
          TagButton("상큼한")
          Spacer(modifier = Modifier.width(10.dp))
        }
      }
    }
    Column() {
      Text(
        text = "칵테일 설명",
        fontSize = 16.sp,
        fontWeight = FontWeight.Bold
      )
      Spacer(modifier = Modifier.height(10.dp))
      Text(
        fontSize = 12.sp,
        text = "진을 베이스로 한 분홍색 칵테일\n" +
            "색깔을 내기 위해 그레나딘 시럽을 넣으며, 계란 흰자와 크림을 추가하여\n" +
            "입에 닿는 느낌은 비교적 부드러운 편\n" +
            "진 베이스 칵테일 입문으로 하기 좋은 칵테일"
      )
    }
    Row() {
      Text(
        fontSize = 16.sp,
        text = "필요한 재료",
        modifier = Modifier.weight(0.3f),
        fontWeight = FontWeight.Bold
      )
      Column(modifier = Modifier.weight(0.7f)) {
        Text(text = "재료1")
        Text(text = "재료2")
      }
    }
  }
  Spacer(modifier = Modifier.height(20.dp))

}

@Composable
private fun BlurBackImg() {
  Image(
    painter = painterResource(id = R.drawable.img_main_dummy),
    contentDescription = "Img Backgound",
    modifier = Modifier
      .fillMaxSize()
      .blur(15.dp),
    contentScale = ContentScale.Crop
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

@Preview
@Composable
fun PreviewDetailScreen() {
  DetailScreen()
}