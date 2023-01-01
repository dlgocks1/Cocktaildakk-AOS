package com.compose.cocktaildakk_compose.domain.repository

import com.compose.cocktaildakk_compose.domain.model.UserCocktailWeight
import com.compose.cocktaildakk_compose.domain.model.UserInfo
import kotlinx.coroutines.flow.Flow

interface UserInfoRepository {

    suspend fun insertCocktailWeight(userCocktailWeight: UserCocktailWeight)
    suspend fun updateCocktailWeight(userCocktailWeight: UserCocktailWeight)
    fun getCocktailWeight(): Flow<UserCocktailWeight?>

    suspend fun updateUserInfo(userInfo: UserInfo)
    suspend fun insertUserInfo(userInfo: UserInfo)
    fun getUserInfo(): Flow<UserInfo?>
}
