package com.compose.cocktaildakk_compose.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import com.compose.cocktaildakk_compose.data.data_source.UserInfoDao
import com.compose.cocktaildakk_compose.domain.model.CocktailWeight
import com.compose.cocktaildakk_compose.domain.model.UserInfo
import com.compose.cocktaildakk_compose.domain.repository.UserInfoRepository
import com.compose.cocktaildakk_compose.ui.mypage.MypageViewModel
import com.google.firebase.firestore.remote.Datastore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

class UserInfoRepositoryImpl @Inject constructor(
  private val userInfoDao: UserInfoDao,
) : UserInfoRepository {

  override suspend fun insertUserInfo(userInfo: UserInfo) {
    return userInfoDao.insert(userInfo)
  }

  override suspend fun getUserInfo(): Flow<UserInfo?> {
    return userInfoDao.getUserInfo()
  }

  override suspend fun updateUserInfo(userInfo: UserInfo) {
    return userInfoDao.update(userInfo)
  }

  override suspend fun insertCocktailWeight(cocktailWeight: CocktailWeight) {
    return userInfoDao.insertWeight(cocktailWeight = cocktailWeight)
  }

  override suspend fun getCocktailWeight(): Flow<CocktailWeight?> {
    return userInfoDao.getWeight()
  }

  override suspend fun updateCocktailWeight(cocktailWeight: CocktailWeight) {
    return userInfoDao.updateWeight(cocktailWeight = cocktailWeight)
  }
}