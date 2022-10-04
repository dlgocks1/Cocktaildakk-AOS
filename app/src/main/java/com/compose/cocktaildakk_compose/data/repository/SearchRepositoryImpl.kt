package com.compose.cocktaildakk_compose.data.repository

import com.compose.cocktaildakk_compose.data.data_source.RecentStrDao
import com.compose.cocktaildakk_compose.data.data_source.RecentStrDataBase
import com.compose.cocktaildakk_compose.domain.model.RecentStr
import com.compose.cocktaildakk_compose.domain.repository.SearchRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(
	private val recentStrDao: RecentStrDao
) : SearchRepository {
	override fun getResentSearchAll(): Flow<List<RecentStr>> = flow {
		recentStrDao.recentStrAll().collectLatest {
			emit(it)
		}
	}

	override suspend fun addSearchStr(seartchStr: String) {
		recentStrDao.insert(RecentStr(seartchStr))
	}

	override suspend fun removeSearchStr(seartchStr: String) {
		recentStrDao.delete(RecentStr(seartchStr))
	}

	override suspend fun removeAllSearchStr() {
		recentStrDao.deleteAll()
	}
}