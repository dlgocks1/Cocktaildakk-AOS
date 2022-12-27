package com.compose.cocktaildakk_compose.ui.detail.review

import android.graphics.Bitmap
import android.util.Log
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.livedata.observeAsState
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
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.compose.cocktaildakk_compose.R
import com.compose.cocktaildakk_compose.domain.model.Cocktail
import com.compose.cocktaildakk_compose.ui.detail.BlurBackImg
import com.compose.cocktaildakk_compose.ui.detail.DetailViewModel
import com.compose.cocktaildakk_compose.ui.detail.gallery.GalleryViewModel
import com.compose.cocktaildakk_compose.ui.theme.Color_Default_Backgounrd
import com.compose.cocktaildakk_compose.ui.theme.Color_Default_Backgounrd_70
import com.compose.cocktaildakk_compose.ui.theme.ScreenRoot.GALLERY

@Composable
fun ReviewWritingScreen(
    detailViewModel: DetailViewModel = hiltViewModel(),
    navController: NavController = rememberNavController(),
    idx: Int
) {
    val text = remember { mutableStateOf(TextFieldValue("")) }

    val secondScreenResult = navController.currentBackStackEntry
        ?.savedStateHandle
        ?.getLiveData<List<GalleryViewModel.CroppingImage>>("bitmap_images")
        ?.observeAsState()


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
                navController.popBackStack()
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
                StarRating()
                Spacer(modifier = Modifier.height(30.dp))
                PicktureUpload(navController, secondScreenResult)
                Spacer(modifier = Modifier.height(10.dp))
                Column {
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
                            .heightIn(min = 200.dp)
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
                                    // TODO 리뷰 작성완료
                                }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun PicktureUpload(
    navController: NavController,
    secondScreenResult: State<List<GalleryViewModel.CroppingImage>?>?
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
                        navController.navigate(GALLERY)
                    },
                text = "사진 업로드 $picktureCount/5",
                color = Color.White
            )
        }
        PicktureContent(secondScreenResult)
    }
}

@Composable
fun PicktureContent(secondScreenResult: State<List<GalleryViewModel.CroppingImage>?>?) {
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
        Text(
            text = "\'핑크 레이디\'에 대한 별점을 평가해 주세요.",
            fontSize = 16.sp,
            color = Color_Default_Backgounrd_70
        )
        Row {
            for (i in 0..4) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_baseline_star_24),
                    contentDescription = null,
                    modifier = Modifier.size(36.dp),
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