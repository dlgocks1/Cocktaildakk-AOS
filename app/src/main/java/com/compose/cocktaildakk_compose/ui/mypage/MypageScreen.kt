package com.compose.cocktaildakk_compose.ui.mypage

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.compose.cocktaildakk_compose.R
import com.compose.cocktaildakk_compose.ui.search.searchResult.SearchListItem
import com.compose.cocktaildakk_compose.ui.theme.Color_Default_Backgounrd

@Composable
fun MypageScreen() {
  Column(
    modifier = Modifier
      .fillMaxSize()
      .background(color = Color_Default_Backgounrd)
  ) {
    Text(
      text = "마이페이지",
      fontSize = 18.sp,
      modifier = Modifier
        .fillMaxWidth()
        .padding(0.dp, 20.dp), textAlign = TextAlign.Center,
      color = Color.White,
      fontWeight = FontWeight.Bold
    )

    Box(
      modifier = Modifier
        .fillMaxSize()
    ) {
      Box(
        modifier = Modifier
          .fillMaxSize()
          .clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
          .border(5.dp, Color.White, RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
      )
      Box(
        modifier = Modifier
          .fillMaxSize()
          .padding(top = 5.dp)
          .clip(RoundedCornerShape(topStart = 18.dp, topEnd = 18.dp))
          .border(
            5.dp,
            Color_Default_Backgounrd,
            RoundedCornerShape(topStart = 18.dp, topEnd = 18.dp)
          )
      )
      Column(
        modifier = Modifier
          .padding(top = 20.dp, bottom = 20.dp)
          .fillMaxSize()
      ) {
        Row(
          modifier = Modifier
            .padding(20.dp),
          verticalAlignment = Alignment.CenterVertically
        ) {
          Image(
            painter = painterResource(id = R.drawable.img_main_dummy),
            contentDescription = "ProfileImg",
            modifier = Modifier
              .size(80.dp)
              .clip(CircleShape), contentScale = ContentScale.Crop
          )
          Column(modifier = Modifier.offset(x = 20.dp)) {
            Text(text = "닉네임", fontSize = 24.sp)
            Text(text = "닉네임 재설정", fontSize = 12.sp)
          }
        }
        Spacer(
          modifier = Modifier
            .height(5.dp)
            .fillMaxWidth()
            .background(color = Color(0x40ffffff))
        )
        Text(
          text = "나의 키워드 정보",
          modifier = Modifier.padding(start = 20.dp, top = 20.dp),
          fontSize = 18.sp
        )
        Column(
          modifier = Modifier.padding(20.dp),
          verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
          Column(
            verticalArrangement = Arrangement.spacedBy(10.dp)
          ) {
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween,
            ) {
              Text(text = "주량 레벨", fontSize = 16.sp)
              Text(
                text = "주량 레벨 변경하기",
                fontSize = 14.sp,
                color = Color(0x70ffffff)
              )
            }
            Text(
              text = "소주 3~4잔", fontSize = 14.sp, color = Color(0x70ffffff)
            )
          }
          Spacer(
            modifier = Modifier
              .height(2.dp)
              .fillMaxWidth()
              .background(color = Color(0x40ffffff))
          )
          Column(
            verticalArrangement = Arrangement.spacedBy(10.dp)
          ) {
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween,
            ) {
              Text(text = "선호하는 기주", fontSize = 16.sp)
              Text(
                text = "기주 다시 선택하기",
                fontSize = 14.sp,
                color = Color(0x70ffffff)
              )
            }
            Text(
              text = "보드카", fontSize = 14.sp, color = Color(0x70ffffff)
            )
          }
          Spacer(
            modifier = Modifier
              .height(2.dp)
              .fillMaxWidth()
              .background(color = Color(0x40ffffff))
          )
          Column(
            verticalArrangement = Arrangement.spacedBy(10.dp)
          ) {
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween,
            ) {
              Text(text = "내가 선택한 키워드", fontSize = 16.sp)
              Text(
                text = "키워드 다시 선택하기",
                fontSize = 14.sp,
                color = Color(0x70ffffff)
              )
            }
            Text(
              text = "가벼한", fontSize = 14.sp, color = Color(0x70ffffff)
            )
          }
        }
      }
    }
  }
}

@Preview
@Composable
fun MypagePreview() {
  MypageScreen()
}