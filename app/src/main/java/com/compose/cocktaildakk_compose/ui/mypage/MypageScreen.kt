package com.compose.cocktaildakk_compose.ui.mypage

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.compose.cocktaildakk_compose.R
import com.compose.cocktaildakk_compose.ui.components.TagButton
import com.compose.cocktaildakk_compose.ui.theme.Color_Default_Backgounrd
import com.google.accompanist.flowlayout.FlowRow

@Composable
fun MypageScreen(
  mypageViewModel: MypageViewModel = hiltViewModel(),
  navController: NavController = rememberNavController(

  )
) {

  LaunchedEffect(Unit) {

  }

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
            Text(text = mypageViewModel.userInfo.value.nickname, fontSize = 24.sp)
            Spacer(modifier = Modifier.height(10.dp))
            Row(
              horizontalArrangement = Arrangement.spacedBy(5.dp),
              verticalAlignment = Alignment.CenterVertically,
              modifier = Modifier.clickable {
                navController.navigate("modifynickname")
              },
            ) {
              Icon(
                painter = painterResource(id = R.drawable.ic_baseline_refresh_24),
                contentDescription = "Icon Refresh", modifier = Modifier
                  .size(16.dp)
                  .rotate(270f),
                tint = Color(0x70ffffff)
              )
              Text(
                text = "닉네임 재설정",
                fontSize = 14.sp,
                color = Color(0x70ffffff)
              )
            }
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
            verticalArrangement = Arrangement.spacedBy(20.dp)
          ) {
            Row(
              modifier = Modifier.fillMaxWidth(),
              verticalAlignment = Alignment.CenterVertically,
              horizontalArrangement = Arrangement.SpaceBetween,
            ) {
              Text(text = "선호 도수", fontSize = 18.sp)
              Row(
                horizontalArrangement = Arrangement.spacedBy(5.dp),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.clickable {
                  navController.navigate("modifylevel")
                },
              ) {
                Icon(
                  painter = painterResource(id = R.drawable.ic_baseline_refresh_24),
                  contentDescription = "Icon Refresh", modifier = Modifier
                    .size(16.dp)
                    .rotate(270f),
                  tint = Color(0x70ffffff)
                )
                Text(
                  text = "선호 도수 변경하기",
                  fontSize = 14.sp,
                  color = Color(0x70ffffff)
                )
              }
            }
            Text(
              text = "${mypageViewModel.userInfo.value.level} 도",
              fontSize = 16.sp,
              color = Color(0x70ffffff)
            )
          }
          Spacer(
            modifier = Modifier
              .height(2.dp)
              .fillMaxWidth()
              .background(color = Color(0x40ffffff))
          )
          Column(
            verticalArrangement = Arrangement.spacedBy(20.dp)
          ) {
            Row(
              modifier = Modifier.fillMaxWidth(),
              verticalAlignment = Alignment.CenterVertically,
              horizontalArrangement = Arrangement.SpaceBetween,
            ) {
              Text(text = "선호하는 기주", fontSize = 18.sp)
              Row(
                modifier = Modifier.clickable {
                  navController.navigate("modifybase")
                },
                horizontalArrangement = Arrangement.spacedBy(5.dp),
                verticalAlignment = Alignment.CenterVertically,
              ) {
                Icon(
                  painter = painterResource(id = R.drawable.ic_baseline_refresh_24),
                  contentDescription = "Icon Refresh", modifier = Modifier
                    .size(16.dp)
                    .rotate(270f),
                  tint = Color(0x70ffffff)
                )
                Text(
                  text = "기주 다시 선택하기",
                  fontSize = 14.sp,
                  color = Color(0x70ffffff)
                )
              }
            }
            FlowRow(
              modifier = Modifier.fillMaxWidth(),
              crossAxisSpacing = 10.dp,
            ) {
              mypageViewModel.userInfo.value.base.map { i ->
                TagButton(i)
                Spacer(modifier = Modifier.width(10.dp))
              }
            }
          }
          Spacer(
            modifier = Modifier
              .height(2.dp)
              .fillMaxWidth()
              .background(color = Color(0x40ffffff))
          )
          Column(
            verticalArrangement = Arrangement.spacedBy(20.dp)
          ) {
            Row(
              modifier = Modifier.fillMaxWidth(),
              verticalAlignment = Alignment.CenterVertically,
              horizontalArrangement = Arrangement.SpaceBetween,
            ) {
              Text(text = "내가 선택한 키워드", fontSize = 18.sp)
              Row(
                horizontalArrangement = Arrangement.spacedBy(5.dp),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.clickable {
                  navController.navigate("modifykeyword")
                },
              ) {
                Icon(
                  painter = painterResource(id = R.drawable.ic_baseline_refresh_24),
                  contentDescription = "Icon Refresh", modifier = Modifier
                    .size(16.dp)
                    .rotate(270f),
                  tint = Color(0x70ffffff)
                )
                Text(
                  text = "키워드 다시 선택하기",
                  fontSize = 14.sp,
                  color = Color(0x70ffffff)
                )
              }
            }
            FlowRow(
              modifier = Modifier.fillMaxWidth(),
              crossAxisSpacing = 10.dp,
            ) {
              mypageViewModel.userInfo.value.keyword.map { i ->
                TagButton(i)
                Spacer(modifier = Modifier.width(10.dp))
              }
            }
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