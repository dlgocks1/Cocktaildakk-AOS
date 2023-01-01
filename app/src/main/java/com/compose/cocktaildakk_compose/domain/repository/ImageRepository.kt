package com.compose.cocktaildakk_compose.domain.repository

import com.compose.cocktaildakk_compose.domain.model.GalleryImage

interface ImageRepository {
    fun getAllPhotos(
        page: Int,
        loadSize: Int,
        currentLocation: String? = null
    ): MutableList<GalleryImage>

    fun getFolderList(): ArrayList<String>
}