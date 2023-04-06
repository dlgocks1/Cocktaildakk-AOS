package com.compose.cocktaildakk_compose.ui.detail.components

import android.graphics.Bitmap
import android.view.LayoutInflater
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.compose.cocktaildakk_compose.R
import com.compose.cocktaildakk_compose.domain.model.GalleryImage
import com.compose.cocktaildakk_compose.ui.detail.model.CroppingImage
import com.compose.cocktaildakk_compose.ui.detail.model.ImageCropStatus
import com.compose.cocktaildakk_compose.ui.theme.Color_Default_Backgounrd
import com.naver.android.helloyako.imagecrop.view.ImageCropView


@Composable
fun CustomImageCropView(
    showSnackbar: (String) -> Unit,
    modifyingImage: GalleryImage?,
    selectedImages: List<CroppingImage>,
    selecetedStatus: ImageCropStatus,
    setCropStatus: (ImageCropStatus) -> Unit,
    addSelectedImage: (Long, Bitmap) -> Unit,
    setSelectImages: (index: Int, croppingImage: CroppingImage) -> Unit,
) {
    val isSelected = selectedImages.find {
        it.id == modifyingImage?.id
    } != null

    Box(Modifier.height(IntrinsicSize.Min)) {
        AndroidView(
            modifier = Modifier
                .fillMaxWidth(1f)
                .aspectRatio(1f),
            factory = { context ->
                val view = LayoutInflater.from(context).inflate(R.layout.image_crop_view, null)
                val imageCropView = view.findViewById<ImageCropView>(R.id.image_crop_view)
                imageCropView.apply {
                    setAspectRatio(1, 1)
                }
                imageCropView
            },
            update = { view ->
                modifyingImage?.let {
                    view.setImageFilePath(it.filepath)
                }
                if (modifyingImage != null && view.croppedImage != null) {
                    selecetedStatus.isCropping {
                        addSelectedImage(modifyingImage.id, view.croppedImage)
                        setCropStatus(ImageCropStatus.WAITING)
                    }
                    selecetedStatus.isModifying {
                        val index =
                            selectedImages.indexOf(selectedImages.find { it.id == modifyingImage.id })
                        setSelectImages(
                            index,
                            CroppingImage(modifyingImage.id, view.croppedImage)
                        )
                        setCropStatus(ImageCropStatus.WAITING)
                    }
                }
            },
        )

        if (modifyingImage != null) {
            Column(
                modifier = Modifier
                    .padding(20.dp)
                    .align(Alignment.BottomEnd)
                    .clip(RoundedCornerShape(30))
                    .border(1.dp, Color.White, RoundedCornerShape(30))
                    .background(Color_Default_Backgounrd)
                    .clickable {
                        if (isSelected) {
                            setCropStatus(ImageCropStatus.MODIFYING)
                        } else {
                            if (selectedImages.size <= 4) {
                                setCropStatus(ImageCropStatus.CROPPING)
                            } else {
                                showSnackbar("이미지는 5개 이하로 선택해 주세요.")
                            }
                        }
                    },
            ) {
                Text(
                    text = if (isSelected) "이미지 수정하기" else "이미지 추가하기",
                    modifier = Modifier
                        .clip(RoundedCornerShape(30.dp))
                        .padding(20.dp, 10.dp)
                        .background(
                            Color_Default_Backgounrd,
                        ),
                    color = Color.White,
                )
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize(1f),
                verticalArrangement = Arrangement.Center,
            ) {
                Text(
                    text = "업로드할 이미지를 선택해 주세요.",
                    color = Color.White,
                    fontSize = 18.sp,
                    modifier = Modifier
                        .fillMaxWidth(1f),
                    textAlign = TextAlign.Center,
                )
            }
        }
    }
}