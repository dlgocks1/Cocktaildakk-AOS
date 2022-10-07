package com.compose.cocktaildakk_compose.domain.repository

import com.compose.cocktaildakk_compose.domain.model.RecentStr
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.flow.Flow

interface SearchRepository {
  fun getRecentSearchAll(): Flow<List<RecentStr>>
  suspend fun addSearchStr(seartchStr: String)
  suspend fun removeAllSearchStr()
  suspend fun removeSearchStr(searchStr: RecentStr)

}