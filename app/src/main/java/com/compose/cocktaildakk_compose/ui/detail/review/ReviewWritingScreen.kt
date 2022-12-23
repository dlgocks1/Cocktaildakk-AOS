package com.compose.cocktaildakk_compose.ui.detail.review

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.compose.cocktaildakk_compose.R
import com.compose.cocktaildakk_compose.domain.model.Cocktail
import com.compose.cocktaildakk_compose.ui.detail.BlurBackImg
import com.compose.cocktaildakk_compose.ui.theme.Color_Default_Backgounrd

@Composable
fun ReviewWritingScreen(
    navController: NavController = rememberNavController(),
    idx: Int
) {
    val text = remember { mutableStateOf(TextFieldValue("")) }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
        ) {
            BlurBackImg(cocktail = Cocktail())
            TopBar(navController, "리뷰 작성하기")
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
                verticalArrangement = Arrangement.spacedBy(30.dp)
            ) {
                StarRating()
                PicktureUpload()
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "${text.value.text.length} / 150",
                        color = Color.White,
                        textAlign = TextAlign.End,
                        fontSize = 14.sp,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .padding(bottom = 20.dp)
                            .border(1.dp, Color.White),
                    ) {
                        BasicTextField(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(20.dp),
                            value = text.value,
                            onValueChange = {
                                text.value = it
                            },
                            cursorBrush = SolidColor(Color.White),
                            textStyle = TextStyle(fontSize = 14.sp, color = Color.White),
                            decorationBox = { innerTextField ->
                                if (text.value.text.isEmpty()) {
                                    Text(text = "칵테일에 대한 정보 입력", color = Color.Gray)
                                }
                                innerTextField()  //<-- Add this
                            })
                    }
                }
            }
        }
    }
}

@Composable
fun YouserReview() {

}

@Composable
private fun PicktureUpload() {
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
                    .padding(15.dp, 5.dp),
                text = "사진 업로드 0/5",
                color = Color.White
            )
        }
//        PicktureContent()
    }
}

@Composable
fun PicktureContent() {
    Spacer(modifier = Modifier.height(5.dp))
    Row(
        modifier = Modifier.horizontalScroll(rememberScrollState()),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        for (i in 0..4) {
            Image(
                painter = painterResource(id = R.drawable.img_main_dummy),
                contentDescription = null,
                modifier = Modifier.size(90.dp),
                contentScale = ContentScale.Crop
            )
        }
    }

}

@Composable
private fun StarRating() {
    Column(
        modifier = Modifier
            .padding(top = 20.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(5.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(text = "핑크 레이디", fontSize = 30.sp, color = Color.White)
        Text(text = "Pink Lady", fontSize = 20.sp, color = Color.White)
        Spacer(modifier = Modifier.height(15.dp))
        Text(text = "\'핑크 레이디\'에 대한 별점을 평가해 주세요.", fontSize = 16.sp, color = Color.White)
        Row {
            for (i in 0..4) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_baseline_star_24),
                    contentDescription = null,
                    modifier = Modifier.size(28.dp),
                    tint = Color.White
                )
            }
        }
    }
}

@Preview
@Composable
fun PreviewReviewWritingScreen() {
//    ReviewWritingScreen()
}