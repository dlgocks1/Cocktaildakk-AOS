package com.compose.cocktaildakk_compose.data.data_source

import androidx.annotation.WorkerThread
import androidx.room.*
import com.compose.cocktaildakk_compose.domain.model.BookmarkIdx
import com.compose.cocktaildakk_compose.domain.model.Cocktail
import com.compose.cocktaildakk_compose.domain.model.KeywordTag
import kotlinx.coroutines.flow.Flow

@Dao
interface CocktailDao {

  @Query("SELECT * FROM cocktail ORDER BY idx DESC")
  fun getCocktailAll(): Flow<List<Cocktail>>

  @Query("SELECT * FROM cocktail WHERE idx = :idx")
  fun getCocktail(idx: Int): Flow<Cocktail>

  @Query(
    "select * from cocktail WHERE enName LIKE '%' || :searchStr || '%'" +
        "Or keyword LIKE '%' || :searchStr  || '%'" +
        "OR ingredient LIKE '%' || :searchStr  || '%'" +
        "OR krName LIKE '%' || :searchStr  || '%'" +
        " LIMIT :loadSize OFFSET :index * :loadSize"
  )
  fun getCocktailPaging(index: Int, loadSize: Int, searchStr: String): Flow<List<Cocktail>>

  @Query(
    "select COUNT(idx) from cocktail WHERE enName LIKE '%' || :searchStr || '%'" +
        "Or keyword LIKE '%' || :searchStr  || '%'" +
        "OR ingredient LIKE '%' || :searchStr  || '%'" +
        "OR krName LIKE '%' || :searchStr  || '%'"
  )
  fun getCocktailCounts(searchStr: String): Flow<Int>

  @Query(
    "SELECT * FROM cocktail WHERE enName LIKE '%' || :searchStr || '%'" +
        "Or keyword LIKE '%' || :searchStr  || '%' " +
        "OR ingredient LIKE '%' || :searchStr  || '%'" +
        "OR krName LIKE '%' || :searchStr  || '%' ORDER BY idx DESC"
  )
  fun queryCocktail(searchStr: String): Flow<List<Cocktail>>

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insert(cocktail: Cocktail)

  @Update
  suspend fun update(cocktail: Cocktail)

  @Delete
  suspend fun delete(cocktail: Cocktail)

  @Query("DELETE FROM cocktail")
  suspend fun deleteAll()

  @Query("SELECT * FROM bookmarkidx")
  fun getAllBookmark(): Flow<List<BookmarkIdx>>

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertBookmark(bookmarkIdx: BookmarkIdx)

  @Update
  suspend fun updateBookmark(bookmarkIdx: BookmarkIdx)

  @Delete
  suspend fun deleteBookmark(bookmarkIdx: BookmarkIdx)

  @Query("SELECT * FROM keywordtag")
  fun getAllKeyword(): Flow<List<KeywordTag>>

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertKeyword(keywordTag: KeywordTag)

  @Query("DELETE FROM keywordtag")
  suspend fun deleteKeywordAll()
}