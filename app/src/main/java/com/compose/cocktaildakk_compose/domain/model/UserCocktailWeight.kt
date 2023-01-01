package com.compose.cocktaildakk_compose.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class UserCocktailWeight(
    var level: Int = 2,
    var base: Int = 2,
    var keyword: Int = 2,
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 1
}
