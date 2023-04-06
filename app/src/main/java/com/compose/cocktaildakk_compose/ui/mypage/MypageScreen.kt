package com.compose.cocktaildakk_compose.ui.mypage

import androidx.compose.foundation.*
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
import com.compose.cocktaildakk_compose.*
import com.compose.cocktaildakk_compose.R
import com.compose.cocktaildakk_compose.ui.components.TagButton
import com.compose.cocktaildakk_compose.ui.theme.Color_Cyan
import com.compose.cocktaildakk_compose.ui.theme.Color_Default_Backgounrd
import com.compose.cocktaildakk_compose.ui.theme.ScreenRoot
import com.compose.cocktaildakk_compose.ui.theme.ScreenRoot.MODIFY_COCKTAIL_WEIGHT
import com.google.accompanist.flowlayout.FlowRow

@Composable
fun MypageScreen(
    mypageViewModel: MypageViewModel = hiltViewModel(),
    navController: NavController = rememberNavController(),
) {
    LaunchedEffect(Unit) {
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color_Default_Backgounrd),
    ) {
        Text(
            text = MYPAGE_TEXT,
            fontSize = 18.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(0.dp, 20.dp),
            textAlign = TextAlign.Center,
            color = Color.White,
            fontWeight = FontWeight.Bold,
        )

        Box(
            modifier = Modifier
                .fillMaxSize(),
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
                    .border(
                        5.dp,
                        Color.White,
                        RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)
                    ),
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 5.dp)
                    .clip(RoundedCornerShape(topStart = 18.dp, topEnd = 18.dp))
                    .border(
                        5.dp,
                        Color_Default_Backgounrd,
                        RoundedCornerShape(topStart = 18.dp, topEnd = 18.dp),
                    ),
            )
            Column(
                modifier = Modifier
                    .padding(top = 20.dp)
                    .fillMaxSize(),
            ) {
                Row(
                    modifier = Modifier
                        .padding(20.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Image(
                        painter = painterResource(
                            id = if (mypageViewModel.userInfo.value.sex == "Male") {
                                R.drawable.img_male
                            } else if (mypageViewModel.userInfo.value.sex == "Female") {
                                R.drawable.img_female
                            } else {
                                R.drawable.icon_app
                            },
                        ),
                        contentDescription = "ProfileImg",
                        modifier = Modifier
                            .size(80.dp)
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop,
                    )

                    Column(modifier = Modifier.offset(x = 20.dp)) {
                        Text(text = mypageViewModel.userInfo.value.nickname, fontSize = 24.sp)
                        Spacer(modifier = Modifier.height(10.dp))
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(2.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.clickable {
                                navController.navigate(ScreenRoot.MODIFY_NICKNAME)
                            },
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_baseline_refresh_24),
                                contentDescription = null,
                                modifier = Modifier
                                    .size(16.dp)
                                    .rotate(270f),
                                tint = Color(0x70ffffff),
                            )
                            Text(
                                text = NICKNAME_RESET_TEXT,
                                fontSize = 14.sp,
                                color = Color(0x70ffffff),
                            )
                        }
                    }
                }
                Spacer(
                    modifier = Modifier
                        .height(5.dp)
                        .fillMaxWidth()
                        .background(color = Color(0x40ffffff)),
                )
                Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                    Text(
                        text = INFO_MY_KEYWORD_TEXT,
                        modifier = Modifier.padding(start = 20.dp, top = 20.dp),
                        fontSize = 18.sp,
                    )
                    Column(
                        modifier = Modifier.padding(20.dp),
                        verticalArrangement = Arrangement.spacedBy(20.dp),
                    ) {
                        Column(
                            verticalArrangement = Arrangement.spacedBy(20.dp),
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween,
                            ) {
                                Text(text = FAVOR_LEVEL_LEVEL, fontSize = 18.sp)
                                Row(
                                    horizontalArrangement = Arrangement.spacedBy(5.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.clickable {
                                        navController.navigate(ScreenRoot.MODIFY_LEVEL)
                                    },
                                ) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.ic_baseline_refresh_24),
                                        contentDescription = null,
                                        modifier = Modifier
                                            .size(16.dp)
                                            .rotate(270f),
                                        tint = Color(0x70ffffff),
                                    )
                                    Text(
                                        text = RESET_FAVOR_LEVEL_TEXT,
                                        fontSize = 14.sp,
                                        color = Color(0x70ffffff),
                                    )
                                }
                            }
                            Text(
                                text = "${mypageViewModel.userInfo.value.level} $LEVEL_UNIT_TEXT",
                                fontSize = 16.sp,
                                color = Color(0x70ffffff),
                            )
                        }
                        Spacer(
                            modifier = Modifier
                                .height(2.dp)
                                .fillMaxWidth()
                                .background(color = Color(0x40ffffff)),
                        )
                        Column(
                            verticalArrangement = Arrangement.spacedBy(20.dp),
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween,
                            ) {
                                Text(text = "선호하는 기주", fontSize = 18.sp)
                                Row(
                                    modifier = Modifier.clickable {
                                        navController.navigate(ScreenRoot.MODIFY_BASE)
                                    },
                                    horizontalArrangement = Arrangement.spacedBy(5.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                ) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.ic_baseline_refresh_24),
                                        contentDescription = null,
                                        modifier = Modifier
                                            .size(16.dp)
                                            .rotate(270f),
                                        tint = Color(0x70ffffff),
                                    )
                                    Text(
                                        text = RESET_BASE_TEXT,
                                        fontSize = 14.sp,
                                        color = Color(0x70ffffff),
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
                                .background(color = Color(0x40ffffff)),
                        )
                        Column(
                            verticalArrangement = Arrangement.spacedBy(20.dp),
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween,
                            ) {
                                Text(text = MY_SELECTED_KEYWORD_TEXT, fontSize = 18.sp)
                                Row(
                                    horizontalArrangement = Arrangement.spacedBy(5.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.clickable {
                                        navController.navigate(ScreenRoot.MODIFY_KEYWORD)
                                    },
                                ) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.ic_baseline_refresh_24),
                                        contentDescription = null,
                                        modifier = Modifier
                                            .size(16.dp)
                                            .rotate(270f),
                                        tint = Color(0x70ffffff),
                                    )
                                    Text(
                                        text = RESET_KEYWORD_TEXT,
                                        fontSize = 14.sp,
                                        color = Color(0x70ffffff),
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
                    Spacer(
                        modifier = Modifier
                            .height(2.dp)
                            .fillMaxWidth()
                            .background(color = Color(0x40ffffff)),
                    )
                    Row(
                        modifier = Modifier
                            .padding(20.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .border(1.dp, Color_Cyan, RoundedCornerShape(10.dp))
                            .background(Color_Cyan),
                        horizontalArrangement = Arrangement.SpaceBetween,
                    ) {
                        Text(
                            text = RESET_USER_WEIGHT_TEXT,
                            color = Color_Default_Backgounrd,
                            modifier = Modifier
                                .padding(15.dp, 3.dp)
                                .clickable {
                                    navController.navigate(MODIFY_COCKTAIL_WEIGHT)
                                },
                            fontWeight = FontWeight.Bold,
                        )
                    }
                }
            }
        }
    }
}
