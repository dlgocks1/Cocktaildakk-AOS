package com.compose.cocktaildakk_compose.domain.model

import android.graphics.Bitmap

/**
 * @param images는 URL타입으로 파이어스토어에 저장되어 있는 값
 */
data class Review(
    val idx: Int = -1,
    val rankScore: Int = 5,
    val images: List<String> = emptyList(),
    val contents: String = "",
    val userInfo: UserInfo = UserInfo()
) : java.io.Serializable
