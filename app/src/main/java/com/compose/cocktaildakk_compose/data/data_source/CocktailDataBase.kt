package com.compose.cocktaildakk_compose.data.data_source

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.compose.cocktaildakk_compose.domain.model.*

@Database(
    entities = [KeywordTag::class, BookmarkIdx::class, UserCocktailWeight::class, UserInfo::class, Cocktail::class],
    version = 5
)
@TypeConverters(CocktailListConverters::class)
abstract class CocktailDataBase : RoomDatabase() {
    abstract fun cocktailDao(): CocktailDao
    abstract fun userInfoDao(): UserInfoDao
}
