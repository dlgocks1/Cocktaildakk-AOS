package com.compose.cocktaildakk_compose.ui.navigation

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.navArgument
import com.compose.cocktaildakk_compose.ui.ApplicationState
import com.compose.cocktaildakk_compose.ui.Screen
import com.compose.cocktaildakk_compose.ui.bookmark.BookmarkScreen
import com.compose.cocktaildakk_compose.ui.detail.DetailScreen
import com.compose.cocktaildakk_compose.ui.detail.review.ReviewDetailScreen
import com.compose.cocktaildakk_compose.ui.detail.review.ReviewWritingScreen
import com.compose.cocktaildakk_compose.ui.home.HomeScreen
import com.compose.cocktaildakk_compose.ui.mypage.MypageScreen
import com.compose.cocktaildakk_compose.ui.mypage.modify.*
import com.compose.cocktaildakk_compose.ui.naverMap.NaverMapScreen
import com.compose.cocktaildakk_compose.ui.onboarding.*
import com.compose.cocktaildakk_compose.ui.search.searchResult.SearchResultScreen
import com.compose.cocktaildakk_compose.ui.search.searchResult.SearchResultViewModel
import com.compose.cocktaildakk_compose.ui.theme.ScreenRoot
import com.compose.cocktaildakk_compose.ui.theme.ScreenRoot.DETAIL_FORMAT
import com.compose.cocktaildakk_compose.ui.theme.ScreenRoot.DETAIL_REVIEW_FORMAT
import com.compose.cocktaildakk_compose.ui.theme.ScreenRoot.DETAIL_REVIEW_WRITING_FORMAT
import com.compose.cocktaildakk_compose.ui.theme.ScreenRoot.IDX
import com.compose.cocktaildakk_compose.ui.theme.ScreenRoot.MAIN_GRAPH
import com.compose.cocktaildakk_compose.ui.theme.ScreenRoot.MODIFY_BASE
import com.compose.cocktaildakk_compose.ui.theme.ScreenRoot.MODIFY_COCKTAIL_WEIGHT
import com.compose.cocktaildakk_compose.ui.theme.ScreenRoot.MODIFY_KEYWORD
import com.compose.cocktaildakk_compose.ui.theme.ScreenRoot.MODIFY_LEVEL
import com.compose.cocktaildakk_compose.ui.theme.ScreenRoot.MODIFY_NICKNAME

fun NavGraphBuilder.onboardGraph(appState: ApplicationState) {
    navigation(startDestination = ScreenRoot.ONBOARD_START, route = ScreenRoot.ONBOARD_GRAPH) {
        composable(ScreenRoot.ONBOARD_START) { entry ->
            val backStackEntry = remember(entry) {
                appState.navController.getBackStackEntry(ScreenRoot.ONBOARD_GRAPH)
            }
            val onboardViewModel: OnboardViewModel = hiltViewModel(backStackEntry)
            OnboardStartScreen(appState.navController, onboardViewModel = onboardViewModel)
        }
        composable(ScreenRoot.ONBOARD_NICKNAME) {
            val backStackEntry = remember(it) {
                appState.navController.getBackStackEntry(ScreenRoot.ONBOARD_GRAPH)
            }
            val onboardViewModel: OnboardViewModel = hiltViewModel(backStackEntry)
            OnboardNicknameScreen(
                appState.navController,
                onboardViewModel = onboardViewModel,
                scaffoldState = appState.scaffoldState,
            )
        }
        composable(ScreenRoot.ONBOARD_AGE) {
            val backStackEntry = remember(it) {
                appState.navController.getBackStackEntry(ScreenRoot.ONBOARD_GRAPH)
            }
            val onboardViewModel: OnboardViewModel = hiltViewModel(backStackEntry)
            OnboardAgeScreen(appState.navController, onboardViewModel = onboardViewModel)
        }
        composable(ScreenRoot.ONBOARD_SEX) {
            val backStackEntry = remember(it) {
                appState.navController.getBackStackEntry(ScreenRoot.ONBOARD_GRAPH)
            }
            val onboardViewModel: OnboardViewModel = hiltViewModel(backStackEntry)
            OnboardSexScreen(appState.navController, onboardViewModel = onboardViewModel)
        }
        composable(ScreenRoot.ONBOARD_LEVEL) {
            val backStackEntry = remember(it) {
                appState.navController.getBackStackEntry(ScreenRoot.ONBOARD_GRAPH)
            }
            val onboardViewModel: OnboardViewModel = hiltViewModel(backStackEntry)
            OnboardLevelScreen(appState.navController, onboardViewModel = onboardViewModel)
        }
        composable(ScreenRoot.ONBOARD_BASE) {
            val backStackEntry = remember(it) {
                appState.navController.getBackStackEntry(ScreenRoot.ONBOARD_GRAPH)
            }
            val onboardViewModel: OnboardViewModel = hiltViewModel(backStackEntry)
            OnboardBaseScreen(
                scaffoldState = appState.scaffoldState,
                navController = appState.navController,
                onboardViewModel = onboardViewModel,
            )
        }
        composable(ScreenRoot.ONBOARD_KEYWORD) {
            val backStackEntry = remember(it) {
                appState.navController.getBackStackEntry(ScreenRoot.ONBOARD_GRAPH)
            }
            val onboardViewModel: OnboardViewModel = hiltViewModel(backStackEntry)
            OnboardKeywordScreen(
                scaffoldState = appState.scaffoldState,
                navController = appState.navController,
                onboardViewModel = onboardViewModel,
            )
        }
    }
}

fun NavGraphBuilder.mainGraph(
    appState: ApplicationState,
    searchResultViewModel: SearchResultViewModel,
) {
    navigation(startDestination = Screen.Home.route, route = MAIN_GRAPH) {
        composable(Screen.Home.route) { HomeScreen(appState) }
        composable(Screen.SearchResult.route) { _ ->
            SearchResultScreen(
                navController = appState.navController,
                searchResultViewModel = searchResultViewModel,
            )
        }
        composable(Screen.NaverMap.route) { _ ->
            NaverMapScreen(
                appState = appState,
            )
        }
        composable(Screen.Bookmark.route) {
            BookmarkScreen(
                navController = appState.navController,
                scaffoldState = appState.scaffoldState,
            )
        }
        composable(Screen.Mypage.route) { MypageScreen(navController = appState.navController) }
        composable(MODIFY_BASE) {
            ModifyBaseScreen(
                navController = appState.navController,
                scaffoldState = appState.scaffoldState,
            )
        }
        composable(MODIFY_KEYWORD) {
            ModifyKeywordScreen(
                navController = appState.navController,
                scaffoldState = appState.scaffoldState,
            )
        }
        composable(MODIFY_LEVEL) {
            ModifyLevelScreen(
                navController = appState.navController,
            )
        }
        composable(MODIFY_NICKNAME) {
            ModifyNicknameScreen(
                navController = appState.navController,
                scaffoldState = appState.scaffoldState,
            )
        }
        composable(MODIFY_COCKTAIL_WEIGHT) {
            ModifyCocktailWeightScreen(
                navController = appState.navController,
            )
        }
    }
}

fun NavGraphBuilder.detailGraph(appState: ApplicationState) {
    composable(
        DETAIL_FORMAT,
        arguments = listOf(
            navArgument(IDX) {
                type = NavType.IntType
            },
        ),
    ) { entry ->
        LaunchedEffect(Unit) {
            appState.bottomBarState.value = false
        }
        DetailScreen(
            navController = appState.navController,
            idx = entry.arguments?.getInt(IDX) ?: 0,
        )
    }
    composable(
        DETAIL_REVIEW_FORMAT,
        arguments = listOf(
            navArgument(IDX) {
                type = NavType.IntType
            },
        ),
    ) { entry ->
        ReviewDetailScreen(
            navController = appState.navController,
            idx = entry.arguments?.getInt(IDX) ?: 0,
        )
    }
    composable(
        DETAIL_REVIEW_WRITING_FORMAT,
        arguments = listOf(
            navArgument(IDX) {
                type = NavType.IntType
            },
        ),
    ) { entry ->
        ReviewWritingScreen(
            appState = appState,
            idx = entry.arguments?.getInt(IDX) ?: 0,
        )
    }
}
