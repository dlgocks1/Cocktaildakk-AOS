package com.compose.cocktaildakk_compose.data.repository

import com.compose.cocktaildakk_compose.data.data_source.UserInfoDao
import com.compose.cocktaildakk_compose.domain.model.CocktailWeight
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

    override suspend fun insertCocktailWeight(cocktailWeight: CocktailWeight) {
        return userInfoDao.insertWeight(cocktailWeight = cocktailWeight)
    }

    override fun getCocktailWeight(): Flow<CocktailWeight?> {
        return userInfoDao.getWeight()
    }

    override suspend fun updateCocktailWeight(cocktailWeight: CocktailWeight) {
        return userInfoDao.updateWeight(cocktailWeight = cocktailWeight)
    }
}