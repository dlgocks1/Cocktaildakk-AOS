package com.compose.cocktaildakk_compose.di

import android.app.Application
import androidx.room.Room
import com.compose.cocktaildakk_compose.RECENT_STR_DATABASE
import com.compose.cocktaildakk_compose.data.data_source.RecentStrDao
import com.compose.cocktaildakk_compose.data.data_source.RecentStrDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PersistenceModule {
  @Provides
  @Singleton
  fun provideRecentStrDataBase(
    application: Application
  ): RecentStrDataBase {
    return Room.databaseBuilder(application, RecentStrDataBase::class.java, RECENT_STR_DATABASE)
      .fallbackToDestructiveMigration().build()
  }

  @Provides
  @Singleton
  fun provideInUserInfoDao(recentStrDataBase: RecentStrDataBase): RecentStrDao {
    return recentStrDataBase.recentStrDao()
  }
}