package com.compose.cocktaildakk_compose.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.compose.cocktaildakk_compose.*
import com.compose.cocktaildakk_compose.data.data_source.CocktailListConverters

@Entity
data class UserInfo(
    var level: Int = DEFAULT_LEVEL,
    var sex: String = DEFAULT_SEX,
    var age: Int = DEFAULT_AGE,
    var nickname: String = DEFAULT_NICKNAME,
    @TypeConverters(CocktailListConverters::class)
    var keyword: List<String> = listOf(NO_MATTER),
    @TypeConverters(CocktailListConverters::class)
    var base: List<String> = listOf(),
    var firebaseKey: String = ""
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 1

    fun toHashMap(): HashMap<String, Any> {
        return hashMapOf(
            "age" to age,
            "sex" to sex,
            "level" to level,
            "keyword" to keyword,
            "base" to base,
            "nickname" to nickname
        )
    }


}
