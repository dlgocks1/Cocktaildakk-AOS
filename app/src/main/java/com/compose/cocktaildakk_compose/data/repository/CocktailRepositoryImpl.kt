package com.compose.cocktaildakk_compose.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.floatPreferencesKey
import com.compose.cocktaildakk_compose.data.data_source.CocktailDao
import com.compose.cocktaildakk_compose.data.repository.CocktailRepositoryImpl.PreferencesKeys.VERSION_PREFERENCES_KEY
import com.compose.cocktaildakk_compose.domain.model.BookmarkIdx
import com.compose.cocktaildakk_compose.domain.model.Cocktail
import com.compose.cocktaildakk_compose.domain.repository.CocktailRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

class CocktailRepositoryImpl @Inject constructor(
  private val dataStore: DataStore<Preferences>,
  private val cocktailDao: CocktailDao
) : CocktailRepository {

  object PreferencesKeys {
    val VERSION_PREFERENCES_KEY = floatPreferencesKey("version_prefereences_key")
  }

  override suspend fun setCocktailVersion(version: Float) {
    dataStore.edit { prefs ->
      prefs[VERSION_PREFERENCES_KEY] = version
    }
  }

  override suspend fun getCocktailVersion(): Flow<Float> =
    dataStore.data.catch { exception ->
      if (exception is IOException) {
        exception.printStackTrace()
        emit(emptyPreferences())
      } else {
        throw exception
      }
    }.map { prefs ->
      prefs[VERSION_PREFERENCES_KEY] ?: 0f
    }

  override suspend fun addCocktail(cocktail: Cocktail) {
    return cocktailDao.insert(cocktail)
  }

  override suspend fun getCocktailAll(): Flow<List<Cocktail>> {
    return cocktailDao.getCocktailAll()
  }

  override suspend fun getCocktail(idx: Int): Flow<Cocktail> {
    return cocktailDao.getCocktail(idx)
  }

  override suspend fun updateCocktail(cocktail: Cocktail) {
    return cocktailDao.update(cocktail)
  }

  override suspend fun queryCocktail(query: String): Flow<List<Cocktail>> {
    return cocktailDao.queryCocktail(query)
  }

  override suspend fun getAllBookmark(): Flow<List<BookmarkIdx>> {
    return cocktailDao.getAllBookmark()
  }

  override suspend fun insertBookmark(bookmarkIdx: BookmarkIdx) {
    return cocktailDao.insertBookmark(bookmarkIdx)
  }

  override suspend fun deleteBookmark(bookmarkIdx: BookmarkIdx) {
    return cocktailDao.deleteBookmark(bookmarkIdx)
  }

}