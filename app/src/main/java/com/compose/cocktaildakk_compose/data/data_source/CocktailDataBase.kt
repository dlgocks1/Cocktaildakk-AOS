package com.compose.cocktaildakk_compose.data.data_source

import androidx.room.Database
import androidx.room.RoomDatabase
import com.compose.cocktaildakk_compose.domain.model.Cocktail
import com.compose.cocktaildakk_compose.domain.model.RecentStr

@Database(entities = [Cocktail::class], version = 2)
abstract class CocktailDataBase : RoomDatabase() {
  abstract fun cocktailDao(): CocktailDao
}