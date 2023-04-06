package com.compose.cocktaildakk_compose.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.*
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.*
import com.compose.cocktaildakk_compose.BOOKMARK_EN
import com.compose.cocktaildakk_compose.ui.Screen.Companion.BOTTOM_NAV_ITEMS
import com.compose.cocktaildakk_compose.ui.components.BottomBar
import com.compose.cocktaildakk_compose.ui.components.RootNavhost
import com.compose.cocktaildakk_compose.ui.detail.gallery.GalleryScreen
import com.compose.cocktaildakk_compose.ui.domain.ManageBottomBarState
import com.compose.cocktaildakk_compose.ui.domain.rememberApplicationState
import com.compose.cocktaildakk_compose.ui.navigation.detailGraph
import com.compose.cocktaildakk_compose.ui.navigation.mainGraph
import com.compose.cocktaildakk_compose.ui.navigation.onboardGraph
import com.compose.cocktaildakk_compose.ui.onboarding.OnboardStartScreen
import com.compose.cocktaildakk_compose.ui.search.SearchScreen
import com.compose.cocktaildakk_compose.ui.search.searchResult.SearchResultViewModel
import com.compose.cocktaildakk_compose.ui.splash.SplashScreen
import com.compose.cocktaildakk_compose.ui.theme.CocktailDakkComposeTheme
import com.compose.cocktaildakk_compose.ui.theme.Color_Default_Backgounrd
import com.compose.cocktaildakk_compose.ui.theme.ScreenRoot
import com.compose.cocktaildakk_compose.ui.theme.ScreenRoot.GALLERY
import com.compose.cocktaildakk_compose.ui.theme.ScreenRoot.MAIN_GRAPH
import com.compose.cocktaildakk_compose.ui.theme.ScreenRoot.MODIFY_BASE
import com.compose.cocktaildakk_compose.ui.theme.ScreenRoot.MODIFY_COCKTAIL_WEIGHT
import com.compose.cocktaildakk_compose.ui.theme.ScreenRoot.MODIFY_KEYWORD
import com.compose.cocktaildakk_compose.ui.theme.ScreenRoot.MODIFY_LEVEL
import com.compose.cocktaildakk_compose.ui.theme.ScreenRoot.MODIFY_NICKNAME
import com.compose.cocktaildakk_compose.ui.theme.ScreenRoot.MYPAGE
import com.compose.cocktaildakk_compose.ui.theme.ScreenRoot.ONBOARD_START
import com.compose.cocktaildakk_compose.ui.theme.ScreenRoot.SEARCH
import com.compose.cocktaildakk_compose.ui.theme.ScreenRoot.SPLASH
import com.compose.cocktaildakk_compose.ui.utils.showSnackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CocktailDakkComposeTheme {
                val appState = rememberApplicationState()
                val navBackStackEntry by appState.navController.currentBackStackEntryAsState()
                ManageBottomBarState(navBackStackEntry, appState.bottomBarState)
                RootNavhost(appState)
            }
        }
    }
}

