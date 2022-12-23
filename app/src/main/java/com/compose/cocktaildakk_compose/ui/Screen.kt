package com.compose.cocktaildakk_compose.ui

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.compose.cocktaildakk_compose.BOOKMARK_EN
import com.compose.cocktaildakk_compose.R
import com.compose.cocktaildakk_compose.ui.theme.ScreenRoot
import com.compose.cocktaildakk_compose.ui.theme.ScreenRoot.HOME_ROOT
import com.compose.cocktaildakk_compose.ui.theme.ScreenRoot.SEARCH_RESULT

/** 바텀네비게이션에 사용되는 4가지 바텀 바 */
sealed class Screen(
    val route: String,
    @StringRes val stringResId: Int,
    @DrawableRes val drawableResId: Int? = null,
    @DrawableRes val selecteddrawableResId: Int? = null
) {
    object Home :
        Screen(
            HOME_ROOT,
            R.string.home_screen,
            R.drawable.ic_outline_home_24,
            R.drawable.ic_baseline_home_24
        )

    object SearchResult :
        Screen(
            SEARCH_RESULT,
            R.string.search_screen,
            R.drawable.ic_baseline_search_24,
            R.drawable.ic_baseline_search_24
        )

    object Bookmark :
        Screen(
            BOOKMARK_EN,
            R.string.bookmark_screen,
            R.drawable.ic_outline_bookmark_border_24,
            R.drawable.ic_baseline_bookmark_24
        )

    object Mypage :
        Screen(
            ScreenRoot.MYPAGE,
            R.string.mypage_screen,
            R.drawable.ic_baseline_person_outline_24,
            R.drawable.ic_baseline_person_24
        )
}