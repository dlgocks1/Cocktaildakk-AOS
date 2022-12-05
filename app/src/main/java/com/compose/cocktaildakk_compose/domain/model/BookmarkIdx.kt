package com.compose.cocktaildakk_compose.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class BookmarkIdx(
    @PrimaryKey(autoGenerate = false)
    val idx: Int = 0,
)
