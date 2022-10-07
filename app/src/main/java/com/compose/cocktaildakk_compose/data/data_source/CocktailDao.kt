package com.compose.cocktaildakk_compose.data.data_source

import androidx.room.*
import com.compose.cocktaildakk_compose.domain.model.Cocktail
import kotlinx.coroutines.flow.Flow

@Dao
interface CocktailDao {
  @Query("SELECT * FROM cocktail ORDER BY idx DESC")
  fun getCocktailAll(): Flow<List<Cocktail>>

  @Query("SELECT * FROM cocktail WHERE idx = :idx")
  fun getCocktail(idx: Int): Flow<Cocktail>

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insert(cocktail: Cocktail)

  @Update
  suspend fun update(cocktail: Cocktail)

  @Delete
  suspend fun delete(cocktail: Cocktail)

  @Query("DELETE FROM cocktail")
  suspend fun deleteAll()
}