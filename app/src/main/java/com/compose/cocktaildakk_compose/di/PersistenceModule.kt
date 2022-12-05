package com.compose.cocktaildakk_compose.di

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import androidx.room.Room
import com.compose.cocktaildakk_compose.COCKTAIL_DATABASE
import com.compose.cocktaildakk_compose.DATASTORE_NAME
import com.compose.cocktaildakk_compose.RECENT_STR_DATABASE
import com.compose.cocktaildakk_compose.data.data_source.*
import com.compose.cocktaildakk_compose.ui.utils.NetworkChecker
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
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
    fun provideRecentStrDao(recentStrDataBase: RecentStrDataBase): RecentStrDao {
        return recentStrDataBase.recentStrDao()
    }

    @Provides
    @Singleton
    fun provideCocktailDataBase(
        application: Application
    ): CocktailDataBase {
        return Room
            .databaseBuilder(application, CocktailDataBase::class.java, COCKTAIL_DATABASE)
            .addTypeConverter(CocktailListConverters())
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideCocktailDao(cocktailDataBase: CocktailDataBase): CocktailDao {
        return cocktailDataBase.cocktailDao()
    }

    @Provides
    @Singleton
    fun provideUserInfoDao(cocktailDataBase: CocktailDataBase): UserInfoDao {
        return cocktailDataBase.userInfoDao()
    }

    @Provides
    @Singleton
    fun provideFirebaseStore(): FirebaseFirestore {
        return FirebaseFirestore.getInstance()
    }

    @Singleton
    @Provides
    fun providePreferencesDataStore(@ApplicationContext context: Context): DataStore<Preferences> =
        PreferenceDataStoreFactory.create(
            produceFile = { context.preferencesDataStoreFile(DATASTORE_NAME) }
        )

    @Provides
    @Singleton
    fun provideNetworkChecker(
        @ApplicationContext context: Context
    ): NetworkChecker = NetworkChecker(context)

}