package com.compose.cocktaildakk_compose.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.compose.cocktaildakk_compose.data.data_source.CocktailDao
import com.compose.cocktaildakk_compose.data.data_source.CocktailPagingSource
import com.compose.cocktaildakk_compose.data.repository.CocktailRepositoryImpl.PreferencesKeys.VERSION_PREFERENCES_KEY
import com.compose.cocktaildakk_compose.domain.model.BookmarkIdx
import com.compose.cocktaildakk_compose.domain.model.Cocktail
import com.compose.cocktaildakk_compose.domain.model.KeywordTag
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

    override fun getCocktailVersion(): Flow<Float> =
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

    override fun cocktailPaging(searchStr: String): Flow<PagingData<Cocktail>> {
        return Pager(
            config = PagingConfig(
                pageSize = 7,
            ),
            pagingSourceFactory = {
                CocktailPagingSource(
                    cocktailDao = cocktailDao,
                    searchStr = searchStr
                )
            }
        ).flow
    }

    override suspend fun addCocktail(cocktail: Cocktail) {
        return cocktailDao.insert(cocktail)
    }

    override suspend fun addCocktailList(cocktails: List<Cocktail>) {
        return cocktailDao.insertAll(cocktails)
    }

    override fun getCocktailAll(): Flow<List<Cocktail>> {
        return cocktailDao.getCocktailAll()
    }

    override fun getCocktail(idx: Int): Flow<Cocktail> {
        return cocktailDao.getCocktail(idx)
    }

    override suspend fun updateCocktail(cocktail: Cocktail) {
        return cocktailDao.update(cocktail)
    }

    override fun queryCocktail(query: String): Flow<List<Cocktail>> {
        return cocktailDao.queryCocktail(query)
    }

    override fun getAllBookmark(): Flow<List<BookmarkIdx>> {
        return cocktailDao.getAllBookmark()
    }

    override suspend fun insertBookmark(bookmarkIdx: BookmarkIdx) {
        return cocktailDao.insertBookmark(bookmarkIdx)
    }

    override suspend fun deleteBookmark(bookmarkIdx: BookmarkIdx) {
        return cocktailDao.deleteBookmark(bookmarkIdx)
    }

    override suspend fun deleteAllBookmark() {
        return cocktailDao.deleteAll()
    }

    override fun getAllKeyword(): Flow<List<KeywordTag>> {
        return cocktailDao.getAllKeyword()
    }

    override suspend fun insertKeyword(keywordTag: KeywordTag) {
        return cocktailDao.insertKeyword(keywordTag = keywordTag)
    }

    override suspend fun deleteAllKeyword() {
        return cocktailDao.deleteKeywordAll()
    }

}