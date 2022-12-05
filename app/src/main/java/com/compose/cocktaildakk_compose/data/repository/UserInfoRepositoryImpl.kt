package com.compose.cocktaildakk_compose.data.repository

import com.compose.cocktaildakk_compose.data.data_source.UserInfoDao
import com.compose.cocktaildakk_compose.domain.model.UserCocktailWeight
import com.compose.cocktaildakk_compose.domain.model.UserInfo
import com.compose.cocktaildakk_compose.domain.repository.UserInfoRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserInfoRepositoryImpl @Inject constructor(
    private val userInfoDao: UserInfoDao,
) : UserInfoRepository {

    override suspend fun insertUserInfo(userInfo: UserInfo) {
        return userInfoDao.insert(userInfo)
    }

    override fun getUserInfo(): Flow<UserInfo?> {
        return userInfoDao.getUserInfo()
    }

    override suspend fun updateUserInfo(userInfo: UserInfo) {
        return userInfoDao.update(userInfo)
    }

    override suspend fun insertCocktailWeight(userCocktailWeight: UserCocktailWeight) {
        return userInfoDao.insertWeight(userCocktailWeight = userCocktailWeight)
    }

    override fun getCocktailWeight(): Flow<UserCocktailWeight?> {
        return userInfoDao.getWeight()
    }

    override suspend fun updateCocktailWeight(userCocktailWeight: UserCocktailWeight) {
        return userInfoDao.updateWeight(userCocktailWeight = userCocktailWeight)
    }
}