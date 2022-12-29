package com.compose.cocktaildakk_compose.domain.repository

import androidx.paging.PagingData
import com.compose.cocktaildakk_compose.domain.model.GalleryImage
import kotlinx.coroutines.flow.Flow

interface ImageRepository {
    fun getAllPhotos(
        page: Int,
        loadSize: Int,
        currentLocation: String? = null
    ): MutableList<GalleryImage>

    fun getFolderList(): ArrayList<String>
}