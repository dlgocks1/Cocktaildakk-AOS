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
import com.compose.cocktaildakk_compose.ui.detail.gallery.GalleryScreen
import com.compose.cocktaildakk_compose.ui.navigation.detailGraph
import com.compose.cocktaildakk_compose.ui.navigation.mainGraph
import com.compose.cocktaildakk_compose.ui.navigation.onboardGraph
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
import com.compose.cocktaildakk_compose.ui.theme.ScreenRoot.SEARCH
import com.compose.cocktaildakk_compose.ui.theme.ScreenRoot.SPLASH
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CocktailDakkComposeTheme {
                RootIndex()
            }
        }
    }
}

class ApplicationState(
    val bottomBarState: MutableState<Boolean>,
    val navController: NavHostController,
    val scaffoldState: ScaffoldState,
)

@Composable
private fun rememberApplicationState(
    bottomBarState: MutableState<Boolean> = mutableStateOf(false),
    navController: NavHostController = rememberNavController(),
    scaffoldState: ScaffoldState = rememberScaffoldState(),
) = remember(bottomBarState, navController) {
    ApplicationState(
        bottomBarState,
        navController,
        scaffoldState,
    )
}

/** State값들을 정의한 Composable */
@Composable
private fun RootIndex() {
    val appState = rememberApplicationState()
    val navBackStackEntry by appState.navController.currentBackStackEntryAsState()
    ManageBottomBarState(navBackStackEntry, appState.bottomBarState)
    Surface(modifier = Modifier.fillMaxSize(), color = Color.Transparent) {
        RootNavhost(appState)
    }
}

/** 바텀 네비게이션에 대한 Visibility를 관리한다. */
@Composable
private fun ManageBottomBarState(
    navBackStackEntry: NavBackStackEntry?,
    bottomBarState: MutableState<Boolean>
) {
    when (navBackStackEntry?.destination?.route) {
        ScreenRoot.NAVER_MAP, ScreenRoot.HOME_ROOT, ScreenRoot.SEARCH_RESULT, BOOKMARK_EN, MYPAGE -> {
            bottomBarState.value = true
        }
        SEARCH, SPLASH, MODIFY_BASE, MODIFY_LEVEL, MODIFY_KEYWORD, MODIFY_NICKNAME, MODIFY_COCKTAIL_WEIGHT -> {
            bottomBarState.value = false
        }
    }
}

/** NavHost를 정의하여 Navigation을 관리한다. */
@Composable
private fun RootNavhost(
    appState: ApplicationState
) {
    val searchResultViewModel: SearchResultViewModel = hiltViewModel()

    Scaffold(
        scaffoldState = appState.scaffoldState,
        bottomBar = { BottomBar(appState) }
    ) { innerPadding ->
        NavHost(
            appState.navController,
            startDestination = SPLASH,
            Modifier
                .padding(innerPadding)
                .background(color = Color.Transparent)
        ) {
            composable(SPLASH) {
                SplashScreen(appState)
            }
            onboardGraph(appState)
            mainGraph(appState, searchResultViewModel)
            detailGraph(appState)
            composable(SEARCH) {
                SearchScreen(appState)
            }
            composable(GALLERY) {
                GalleryScreen(appState = appState)
            }
        }
    }
}


/** BottomNavigation Bar를 정의한다. */
@Composable
private fun BottomBar(
    appState: ApplicationState,
    bottomNavItems: List<Screen> = BOTTOM_NAV_ITEMS,
) {
    AnimatedVisibility(
        visible = appState.bottomBarState.value,
        enter = fadeIn() + expandVertically(),
        exit = fadeOut() + shrinkVertically(),
        modifier = Modifier.background(color = Color_Default_Backgounrd)
    ) {
        BottomNavigation(
            modifier = Modifier
                .clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)),
            backgroundColor = Color_Default_Backgounrd,
        ) {
            val navBackStackEntry by appState.navController.currentBackStackEntryAsState()
            val currentDestination = navBackStackEntry?.destination
            bottomNavItems.forEachIndexed { _, screen ->
                val isSelected =
                    currentDestination?.hierarchy?.any { it.route == screen.route } == true
                BottomNavigationItem(
                    icon = {
                        Surface(
                            modifier = Modifier
                                .clip(CircleShape),
                            color = if (isSelected) Color.White else Color.Transparent,
                        ) {
                            Icon(
                                painter = painterResource(
                                    id =
                                    (if (isSelected) screen.selecteddrawableResId else screen.drawableResId)
                                        ?: return@Surface
                                ),
                                contentDescription = null,
                            )
                        }
                    },
                    label =
                    if (isSelected) {
                        { Text(text = stringResource(screen.stringResId), color = Color.White) }
                    } else null,
                    selected = isSelected,
                    onClick = {
                        appState.navController.navigate(screen.route) {
                            popUpTo(MAIN_GRAPH) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                    selectedContentColor = Color_Default_Backgounrd,
                    unselectedContentColor = Color.White,
                )
            }
        }
    }
}


