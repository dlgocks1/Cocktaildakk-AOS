package com.compose.cocktaildakk_compose.ui.domain

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.navigation.NavBackStackEntry
import com.compose.cocktaildakk_compose.BOOKMARK_EN
import com.compose.cocktaildakk_compose.ui.theme.ScreenRoot

@Composable
fun ManageBottomBarState(
    navBackStackEntry: NavBackStackEntry?,
    bottomBarState: MutableState<Boolean>,
) {
    when (navBackStackEntry?.destination?.route) {
        ScreenRoot.NAVER_MAP, ScreenRoot.HOME_ROOT, ScreenRoot.SEARCH_RESULT, BOOKMARK_EN, ScreenRoot.MYPAGE -> {
            bottomBarState.value = true
        }
        ScreenRoot.SEARCH, ScreenRoot.SPLASH, ScreenRoot.MODIFY_BASE, ScreenRoot.MODIFY_LEVEL, ScreenRoot.MODIFY_KEYWORD, ScreenRoot.MODIFY_NICKNAME, ScreenRoot.MODIFY_COCKTAIL_WEIGHT -> {
            bottomBarState.value = false
        }
    }
}