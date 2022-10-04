package com.compose.cocktaildakk_compose.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import com.compose.cocktaildakk_compose.ui.Bookmark.BookmarkScreen
import com.compose.cocktaildakk_compose.ui.Home.HomeScreen
import com.compose.cocktaildakk_compose.ui.Mypage.MypageScreen
import com.compose.cocktaildakk_compose.ui.Search.SearchScreen
import com.compose.cocktaildakk_compose.ui.Search.SearchResult.SearchResultScreen
import com.compose.cocktaildakk_compose.ui.Search.SearchViewModel
import com.compose.cocktaildakk_compose.ui.theme.CocktailDakk_composeTheme
import com.compose.cocktaildakk_compose.ui.theme.Color_Default_Backgounrd
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContent {
			CocktailDakk_composeTheme {
				Surface(modifier = Modifier.fillMaxSize()) {
					RootIndex()
				}
			}
		}
	}
}

@Composable
private fun RootIndex() {
	val bottomBarState = rememberSaveable { (mutableStateOf(true)) }
	val navController = rememberNavController()
	val navBackStackEntry by navController.currentBackStackEntryAsState()
	when (navBackStackEntry?.destination?.route) {
		"home", "searchresult", "bookmark", "mypage" -> {
			bottomBarState.value = true
		}
		"search" -> {
			bottomBarState.value = false
		}
	}
	Surface(modifier = Modifier.fillMaxSize(), color = Color.Transparent) {
		RootNavhost(navController, bottomBarState)
	}
}

@Composable
private fun RootNavhost(navController: NavHostController, bottomBarState: MutableState<Boolean>) {
	val bottomNavItems = listOf(
		Screen.Home,
		Screen.SearchResult,
		Screen.Bookmark,
		Screen.Mypage,
	)
	Scaffold(
		bottomBar = { ButtomBar(navController, bottomNavItems, bottomBarState) }
	) { innerPadding ->
		val searchViewModel: SearchViewModel = hiltViewModel()
		NavHost(
			navController,
			startDestination = "MainGraph",
			Modifier
				.padding(innerPadding)
				.background(color = Color.Transparent)
		) {
			mainGraph(
				navController = navController,
				searchViewModel = searchViewModel
			)
			composable("search") {
				SearchScreen(
					searchViewModel = searchViewModel,
					navController = navController
				)
			}
		}
	}
}

@Composable
private fun ButtomBar(
	navController: NavHostController,
	bottomNavItems: List<Screen>,
	bottomBarState: MutableState<Boolean>
) {
	AnimatedVisibility(
		visible = bottomBarState.value,
		enter = slideInVertically(initialOffsetY = { it }),
		exit = slideOutVertically(targetOffsetY = { it }),
		modifier = Modifier.background(color = Color_Default_Backgounrd)
	) {
		BottomNavigation(
			modifier = Modifier
				.clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)),
			backgroundColor = Color_Default_Backgounrd,
		) {
			val navBackStackEntry by navController.currentBackStackEntryAsState()
			val currentDestination = navBackStackEntry?.destination
			bottomNavItems.forEachIndexed { index, screen ->
				val isSelected = currentDestination?.hierarchy?.any { it.route == screen.route } == true
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
						navController.navigate(screen.route) {
							popUpTo(navController.graph.findStartDestination().id) {
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

fun NavGraphBuilder.mainGraph(
	navController: NavController,
	searchViewModel: SearchViewModel
) {
	navigation(startDestination = Screen.Home.route, route = "MainGraph") {
		composable(Screen.Home.route) { HomeScreen(navController) }
		composable(Screen.SearchResult.route) {
			SearchResultScreen(
				searchViewModel = searchViewModel,
				navController = navController
			)
		}
		composable(Screen.Bookmark.route) { BookmarkScreen() }
		composable(Screen.Mypage.route) { MypageScreen() }
	}
}

