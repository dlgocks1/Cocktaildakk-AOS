package com.compose.cocktaildakk_compose.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json

@Entity
data class KeywordTag(
    @Json(name = "tagName")
    val tagName: String = "",
    @PrimaryKey(autoGenerate = false)
    @Json(name = "idx")
    val idx: Int = 0,
)