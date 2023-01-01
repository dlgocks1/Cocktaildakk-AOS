package com.compose.cocktaildakk_compose.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class RecentStr(
    val value: String,
) {
    @PrimaryKey(autoGenerate = true)
    var id = 0
}
