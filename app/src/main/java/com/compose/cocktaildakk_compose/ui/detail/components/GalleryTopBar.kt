package com.compose.cocktaildakk_compose.ui.detail.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.compose.cocktaildakk_compose.R
import com.compose.cocktaildakk_compose.ui.detail.model.CroppingImage
import com.compose.cocktaildakk_compose.ui.theme.Color_Default_Backgounrd
import com.compose.cocktaildakk_compose.ui.theme.Color_White_70

@Composable
fun GalleryTopBar(
    confirmCropImages: () -> Unit,
    showSnackbar: (String) -> Unit,
    popBackStack: () -> Unit,
    currentDirectory: Pair<String, String?>,
    selectedImages: List<CroppingImage>,
    directories: List<Pair<String, String?>>,
    setCurrentDirectory: (Pair<String, String?>) -> Unit,
) {
    var isDropdownMenuExpanded by remember {
        mutableStateOf(false)
    }
    val nothingSelected = selectedImages.isEmpty()

    Row(
        Modifier
            .fillMaxWidth()
            .padding(20.dp, 10.dp)
            .height(40.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = "취소",
            color = Color.White,
            modifier = Modifier.clickable {
                popBackStack()
            },
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .weight(1f)
                .clickable {
                    isDropdownMenuExpanded = true
                },
        ) {
            Text(
                text = currentDirectory.first,
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
            )
            Icon(
                painter = painterResource(id = R.drawable.ic_baseline_arrow_right_24),
                modifier = Modifier
                    .rotate(if (isDropdownMenuExpanded) 270f else 90f)
                    .size(32.dp),
                contentDescription = null,
                tint = Color.White,
            )
        }
        DropdownMenu(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color_Default_Backgounrd)
                .border(
                    BorderStroke(1.dp, Color.White.copy(alpha = 0.8f)),
                ),
            expanded = isDropdownMenuExpanded,
            onDismissRequest = { isDropdownMenuExpanded = false },
        ) {
            directories.map {
                DropdownMenuItem(
                    onClick = {
                        isDropdownMenuExpanded = false
                        setCurrentDirectory(it)
                    }) {
                    Text(
                        text = it.first,
                        color = Color.White,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth(),
                    )
                }
            }
        }

        Text(
            text = "확인",
            color = if (nothingSelected) Color_White_70 else Color.White,
            modifier = Modifier.clickable {
                if (nothingSelected) {
                    showSnackbar("하나 이상의 사진을 추가해 주세요.")
                } else {
                    confirmCropImages()
                }
            },
        )
    }
}
