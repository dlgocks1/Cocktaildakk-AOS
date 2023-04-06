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
    var userKey: String = "",
    var bookmarkKey: String = "",
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 1

    /** Firebase에 Json형식으로 저장하기 위해 HashMap으로 바꾸기 */
    fun toHashMap(): HashMap<String, Any> {
        return hashMapOf(
            "age" to age,
            "sex" to sex,
            "level" to level,
            "keyword" to keyword,
            "base" to base,
            "nickname" to nickname,
        )
    }

    fun isDefault() = this == default()

    companion object {
        fun default() = UserInfo()

        const val MIN_NICKNAME_LENGTH = 3
        const val MAX_NICKNAME_LENGTH = 10
    }
}
