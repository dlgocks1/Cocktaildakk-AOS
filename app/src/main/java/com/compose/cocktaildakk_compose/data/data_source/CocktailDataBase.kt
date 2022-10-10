package com.compose.cocktaildakk_compose.data.data_source

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.compose.cocktaildakk_compose.domain.model.BookmarkIdx
import com.compose.cocktaildakk_compose.domain.model.Cocktail
import com.compose.cocktaildakk_compose.domain.model.CocktailWeight
import com.compose.cocktaildakk_compose.domain.model.UserInfo

@Database(
  entities = [BookmarkIdx::class, CocktailWeight::class, UserInfo::class, Cocktail::class],
  version = 5
)
@TypeConverters(CocktailListConverters::class)
abstract class CocktailDataBase : RoomDatabase() {
  abstract fun cocktailDao(): CocktailDao
  abstract fun userInfoDao(): UserInfoDao
}
