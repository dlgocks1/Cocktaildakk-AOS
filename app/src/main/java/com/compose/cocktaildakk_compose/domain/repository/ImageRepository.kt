package com.compose.cocktaildakk_compose.domain.repository

import com.compose.cocktaildakk_compose.domain.model.GalleryImage

interface ImageRepository {
    fun getAllPhotos(loadSize: Int = 0, currentLocation: String? = null): MutableList<GalleryImage>
}