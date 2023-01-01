package com.compose.cocktaildakk_compose.domain.model

import android.net.Uri

data class GalleryImage(
    val id: Long,
    val filepath: String,
    val uri: Uri,
    val name: String,
    val date: String,
    val size: Int,
    var isSelected: Boolean = false,
)
