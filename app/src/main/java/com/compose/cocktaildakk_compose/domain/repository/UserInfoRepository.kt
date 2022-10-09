package com.compose.cocktaildakk_compose.domain.repository

import com.compose.cocktaildakk_compose.domain.model.CocktailWeight
import com.compose.cocktaildakk_compose.domain.model.UserInfo
import kotlinx.coroutines.flow.Flow

interface UserInfoRepository {

  suspend fun insertCocktailWeight(cocktailWeight: CocktailWeight)
  suspend fun updateCocktailWeight(cocktailWeight: CocktailWeight)
  suspend fun getCocktailWeight(): Flow<CocktailWeight?>

  suspend fun updateUserInfo(userInfo: UserInfo)
  suspend fun insertUserInfo(userInfo: UserInfo)
  suspend fun getUserInfo(): Flow<UserInfo?>
}