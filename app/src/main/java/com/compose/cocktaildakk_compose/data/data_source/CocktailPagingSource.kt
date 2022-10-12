package com.compose.cocktaildakk_compose.data.data_source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.compose.cocktaildakk_compose.domain.model.Cocktail
import kotlinx.coroutines.flow.first

class CocktailPagingSource(
  private val searchStr: String = "",
  private val cocktailDao: CocktailDao
) : PagingSource<Int, Cocktail>() {

  private companion object {
    const val INIT_PAGE_INDEX = 0
  }

  override fun getRefreshKey(state: PagingState<Int, Cocktail>): Int? {
    return state.anchorPosition?.let { achorPosition ->
      state.closestPageToPosition(achorPosition)?.prevKey?.plus(1)
        ?: state.closestPageToPosition(achorPosition)?.nextKey?.minus(1)
    }
  }

  override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Cocktail> {
    val position = params.key ?: INIT_PAGE_INDEX
    val loadData =
      cocktailDao.getCocktailPaging(
        index = position, loadSize = params.loadSize,
        searchStr = searchStr
      ).first()

    return LoadResult.Page(
      data = loadData,
      prevKey = if (position == INIT_PAGE_INDEX) null else position - 1,
      nextKey = if (loadData.isNullOrEmpty()) null else position + 1
    )
  }
}
