package com.compose.cocktaildakk_compose.data.data_source

import androidx.room.Database
import androidx.room.RoomDatabase
import com.compose.cocktaildakk_compose.domain.model.RecentStr

@Database(entities = [RecentStr::class], version = 1)
abstract class RecentStrDataBase : RoomDatabase() {
    abstract fun recentStrDao(): RecentStrDao
}