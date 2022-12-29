package com.compose.cocktaildakk_compose.domain.repository

import android.graphics.Bitmap
import com.compose.cocktaildakk_compose.domain.model.Review
import com.compose.cocktaildakk_compose.domain.model.UserInfo

interface ReviewRepository {
    fun writeReview(
        review: Review,
        onSuccess: () -> Unit,
        onFailed: () -> Unit
    )

    suspend fun putDataToStorage(
        setLoadingState: (Int) -> Unit,
        images: List<Bitmap>,
        userinfo: UserInfo
    ): List<String>
}