package com.compose.cocktaildakk_compose.domain.repository

import com.compose.cocktaildakk_compose.domain.model.BookmarkIdx
import com.compose.cocktaildakk_compose.domain.model.Cocktail
import kotlinx.coroutines.flow.Flow
import java.util.prefs.Preferences

interface CocktailRepository {

  suspend fun setCocktailVersion(version: Float)
  suspend fun getCocktailVersion(): Flow<Float>
  suspend fun addCocktail(cocktail: Cocktail)
  suspend fun getCocktailAll(): Flow<List<Cocktail>>
  suspend fun getCocktail(idx: Int): Flow<Cocktail>
  suspend fun updateCocktail(cocktail: Cocktail)
  suspend fun queryCocktail(query: String): Flow<List<Cocktail>>

  suspend fun getAllBookmark(): Flow<List<BookmarkIdx>>
  suspend fun insertBookmark(bookmarkIdx: BookmarkIdx)
  suspend fun deleteBookmark(bookmarkIdx: BookmarkIdx)
  suspend fun deleteAllBookmark()
}