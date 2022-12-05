package com.compose.cocktaildakk_compose.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.compose.cocktaildakk_compose.data.data_source.CocktailListConverters

@Entity
data class UserInfo(
    var level: Int = 5,
    var sex: String = "Unknown",
    var age: Int = 20,
    var nickname: String = "익명의 누군가",
    @TypeConverters(CocktailListConverters::class)
    var keyword: List<String> = listOf("상관 없음"),
    @TypeConverters(CocktailListConverters::class)
    var base: List<String> = listOf()
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 1
}
