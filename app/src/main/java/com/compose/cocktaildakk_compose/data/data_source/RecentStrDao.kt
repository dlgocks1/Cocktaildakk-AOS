package com.compose.cocktaildakk_compose.data.data_source

import androidx.room.*
import com.compose.cocktaildakk_compose.domain.model.RecentStr
import kotlinx.coroutines.flow.Flow

@Dao
interface RecentStrDao {

	@Query("SELECT * FROM recentStr ORDER BY id DESC")
	fun recentStrAll(): Flow<List<RecentStr>>

	@Insert(onConflict = OnConflictStrategy.REPLACE)
	suspend fun insert(recentStr: RecentStr)

	@Update
	suspend fun update(recentStr: RecentStr)

	@Delete
	suspend fun delete(recentStr: RecentStr)

	@Query("DELETE FROM recentStr")
	suspend fun deleteAll()
}