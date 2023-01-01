package com.compose.cocktaildakk_compose.data.data_source

import androidx.room.*
import com.compose.cocktaildakk_compose.domain.model.UserCocktailWeight
import com.compose.cocktaildakk_compose.domain.model.UserInfo
import kotlinx.coroutines.flow.Flow

@Dao
interface UserInfoDao {
    @Query("SELECT * FROM usercocktailweight")
    fun getWeight(): Flow<UserCocktailWeight?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWeight(userCocktailWeight: UserCocktailWeight)

    @Update
    suspend fun updateWeight(userCocktailWeight: UserCocktailWeight)

    @Query("SELECT * FROM userinfo")
    fun getUserInfo(): Flow<UserInfo?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(userinfo: UserInfo)

    @Update
    suspend fun update(userinfo: UserInfo)

    @Delete
    suspend fun delete(userinfo: UserInfo)

    @Query("DELETE FROM userinfo")
    suspend fun deleteAll()
}
