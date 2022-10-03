package com.compose.cocktaildakk_compose.ui

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.compose.cocktaildakk_compose.R

sealed class Screen(
  val route: String,
  @StringRes val stringResId: Int,
  @DrawableRes val drawableResId: Int? = null,
  @DrawableRes val selecteddrawableResId: Int? = null
) {
  object Home :
    Screen(
      "home",
      R.string.home_screen,
      R.drawable.ic_outline_home_24,
      R.drawable.ic_baseline_home_24
    )

  object SearchResult :
    Screen(
      "searchresult",
      R.string.search_screen,
      R.drawable.ic_baseline_search_24,
      R.drawable.ic_baseline_search_24
    )

  object Bookmark :
    Screen(
      "bookmark",
      R.string.bookmark_screen,
      R.drawable.ic_outline_bookmark_border_24,
      R.drawable.ic_baseline_bookmark_24
    )

  object Mypage :
    Screen(
      "mypage",
      R.string.mypage_screen,
      R.drawable.ic_baseline_person_outline_24,
      R.drawable.ic_baseline_person_24
    )
}