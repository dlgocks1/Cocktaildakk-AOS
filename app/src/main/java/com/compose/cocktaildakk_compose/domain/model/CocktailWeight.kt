package com.compose.cocktaildakk_compose.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CocktailWeight(
    var leveldWeight: Int = 2,
    var baseWeight: Int = 2,
    var keywordWeight: Int = 2,
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 1
}