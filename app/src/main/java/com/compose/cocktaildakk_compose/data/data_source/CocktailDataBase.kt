package com.compose.cocktaildakk_compose.data.data_source

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.compose.cocktaildakk_compose.domain.model.Cocktail
import com.compose.cocktaildakk_compose.domain.model.RecentStr
import com.compose.cocktaildakk_compose.domain.model.UserInfo

@Database(entities = [UserInfo::class, Cocktail::class], version = 4)
@TypeConverters(Converters::class)
abstract class CocktailDataBase : RoomDatabase() {
  abstract fun cocktailDao(): CocktailDao
  abstract fun userInfoDao(): UserInfoDao
}
