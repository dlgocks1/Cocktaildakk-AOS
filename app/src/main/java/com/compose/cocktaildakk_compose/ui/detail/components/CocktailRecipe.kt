package com.compose.cocktaildakk_compose.ui.detail.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.compose.cocktaildakk_compose.RECIPE_TEXT
import com.compose.cocktaildakk_compose.domain.model.Cocktail
import com.compose.cocktaildakk_compose.ui.theme.Color_Default_Backgounrd

@Composable
fun CoktailRecipe(cocktail: Cocktail, colorList: List<Long>) {
    Column(modifier = Modifier.padding(20.dp)) {
        Surface(modifier = Modifier.padding(20.dp), color = Color.Transparent) {
            Text(
                text = RECIPE_TEXT,
                fontSize = 18.sp,
                color = Color.White,
                modifier = Modifier
                    .clip(RoundedCornerShape(10.dp))
                    .border(1.dp, Color.White, RoundedCornerShape(10.dp))
                    .padding(15.dp, 3.dp),
            )
        }
        Row(
            modifier = Modifier
                .padding(20.dp)
                .height(IntrinsicSize.Min)
                .heightIn(min = 150.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            GradientTexts(
                cocktail = cocktail,
                colorList = colorList
            )
            DrawGradientCanvas(
                cocktail = cocktail,
                colorList = colorList
            )
        }
    }
}

@Composable
private fun RowScope.DrawGradientCanvas(
    cocktail: Cocktail,
    colorList: List<Long>,
) {
    Box(
        modifier = Modifier.Companion
            .weight(4f),
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            // 가중치 설정 (최소 15)
            val weightList = cocktail.ingredient.split(',').map {
                val num: String = it.replace("\\D".toRegex(), "")
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
                        strokeWidth = 15f,
                    )
                }
            }
        }

        // 좌, 우 배경 색 삼각형 2개
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
                path = trianglePath,
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
                path = trianglePath,
            )
        }
    }
}

@Composable
private fun RowScope.GradientTexts(
    cocktail: Cocktail,
    colorList: List<Long>,
) {
    Column(
        modifier = Modifier.Companion
            .weight(6f),
        verticalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.Start,
    ) {
        Column(
            modifier = Modifier,
            verticalArrangement = Arrangement.spacedBy(5.dp),
            horizontalAlignment = Alignment.Start,
        ) {
            cocktail.ingredient.split(',').mapIndexed { index, it ->
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Canvas(modifier = Modifier.size(30.dp)) {
                        val canvasWidth = size.width
                        val canvasHeight = size.height
                        drawCircle(
                            radius = size.minDimension / 4,
                            color = Color(colorList[index]),
                            center = Offset(x = canvasWidth / 2, y = canvasHeight / 2),
                        )
                    }
                    Text(modifier = Modifier.offset(x = 10.dp), text = it.trim())
                }
            }
        }
    }
}