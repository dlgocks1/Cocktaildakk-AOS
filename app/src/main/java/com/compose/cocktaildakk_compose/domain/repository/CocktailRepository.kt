package com.compose.cocktaildakk_compose.domain.repository

import androidx.paging.PagingData
import com.compose.cocktaildakk_compose.domain.model.BookmarkIdx
import com.compose.cocktaildakk_compose.domain.model.Cocktail
import com.compose.cocktaildakk_compose.domain.model.Cocktails
import com.compose.cocktaildakk_compose.domain.model.KeywordTag
import kotlinx.coroutines.flow.Flow

interface CocktailRepository {

    fun cocktailPaging(searchStr: String): Flow<PagingData<Cocktail>>

    suspend fun setCocktailVersion(version: Float)
    fun getCocktailVersion(): Flow<Float>
    suspend fun addCocktail(cocktail: Cocktail)
    suspend fun addCocktailList(cocktails: Cocktails)
    fun getCocktailAll(): Flow<List<Cocktail>>
    fun getCocktail(idx: Int): Flow<Cocktail>
    suspend fun updateCocktail(cocktail: Cocktail)
    fun queryCocktail(query: String): Flow<List<Cocktail>>

    fun getAllBookmark(): Flow<List<BookmarkIdx>>
    suspend fun insertBookmark(bookmarkIdx: BookmarkIdx)
    suspend fun deleteBookmark(bookmarkIdx: BookmarkIdx)
    suspend fun deleteAllBookmark()

    fun getAllKeyword(): Flow<List<KeywordTag>>
    suspend fun insertKeyword(keywordTag: KeywordTag)
    suspend fun deleteAllKeyword()
}
