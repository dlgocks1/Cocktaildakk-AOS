@file:OptIn(ExperimentalFoundationApi::class)

package com.compose.cocktaildakk_compose.ui.detail.components

import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.compose.cocktaildakk_compose.domain.model.Cocktail
import com.compose.cocktaildakk_compose.ui.detail.model.CroppingImage


@Composable
fun ContentUpload(
    launcher: ManagedActivityResultLauncher<String, Boolean>,
    secondScreenResult: List<CroppingImage>?,
    cocktailDetail: Cocktail,
    rankScore: Int,
    setRankScore: (Int) -> Unit,
    navigateToGallery: () -> Unit,
    bringIntoViewRequester: BringIntoViewRequester,
    userContents: TextFieldValue,
    updateUserContent: (TextFieldValue) -> Unit,
    addReviewToFirebase: () -> Unit,
    showSnackbar: (String) -> Unit,
) {
    Column(
        Modifier
            .fillMaxSize()
            .padding(20.dp)
            .verticalScroll(rememberScrollState()),
    ) {

        StarRating(
            cocktail = cocktailDetail,
            rankScore = rankScore,
            setRankScore = setRankScore,
        )

        Spacer(modifier = Modifier.height(30.dp))

        PictureUpload(
            galleryLauncher = launcher,
            navigateToGallery = navigateToGallery,
            secondScreenResult = secondScreenResult
        )

        Spacer(modifier = Modifier.height(10.dp))

        Column {
            Text(
                text = "${userContents.text.length} / 150",
                color = Color.White,
                textAlign = TextAlign.End,
                fontSize = 14.sp,
                modifier = Modifier.fillMaxWidth(),
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
                        .padding(20.dp)
                        .bringIntoViewRequester(bringIntoViewRequester),
                    value = userContents,
                    onValueChange = {
                        if (it.text.length <= 150) {
                            updateUserContent(it)
                        }
                    },
                    cursorBrush = SolidColor(Color.White),
                    textStyle = TextStyle(fontSize = 14.sp, color = Color.White),
                    decorationBox = { innerTextField ->
                        if (userContents.text.isEmpty()) {
                            Text(text = "칵테일에 대한 정보 입력", color = Color.Gray)
                        }
                        innerTextField()
                    },
                )
            }
            Box(
                Modifier
                    .fillMaxWidth(1f)
                    .padding(20.dp)
                    .clip(RoundedCornerShape(30))
                    .border(1.dp, Color.White, RoundedCornerShape(30)),
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
                            val picktureCount = (secondScreenResult?.size) ?: 0

                            if (rankScore == 0) {
                                showSnackbar("별점을 입력해 주세요.")
                            }
                            if (picktureCount == 0 || userContents.text
                                    .trim()
                                    .isEmpty()
                            ) {
                                showSnackbar("하나 이상의 사진과 내용을 입력해주세요.")
                            } else {
                                // 파이어베이스에 리뷰 넣기
                                addReviewToFirebase()
                            }
                        },
                )
            }
        }
    }
}