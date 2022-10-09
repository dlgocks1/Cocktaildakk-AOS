package com.compose.cocktaildakk_compose.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.floatPreferencesKey
import com.compose.cocktaildakk_compose.data.data_source.CocktailDao
import com.compose.cocktaildakk_compose.data.data_source.UserInfoDao
import com.compose.cocktaildakk_compose.data.repository.CocktailRepositoryImpl.PreferencesKeys.VERSION_PREFERENCES_KEY
import com.compose.cocktaildakk_compose.domain.model.Cocktail
import com.compose.cocktaildakk_compose.domain.model.UserInfo
import com.compose.cocktaildakk_compose.domain.repository.CocktailRepository
import com.compose.cocktaildakk_compose.domain.repository.UserInfoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

class UserInfoRepositoryImpl @Inject constructor(
  private val userInfoDao: UserInfoDao
) : UserInfoRepository {
  override suspend fun insertUserInfo(userInfo: UserInfo) {
    userInfoDao.insert(userInfo)
  }

  override suspend fun getUserInfo(): Flow<UserInfo?> {
    return userInfoDao.getUserInfo()
  }

  override suspend fun updateUserInfo(userInfo: UserInfo) {
    return userInfoDao.update(userInfo)
  }

}