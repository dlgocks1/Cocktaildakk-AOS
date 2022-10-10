package com.compose.cocktaildakk_compose.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.*
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.*
import androidx.navigation.compose.navigation
import com.compose.cocktaildakk_compose.ui.bookmark.BookmarkScreen
import com.compose.cocktaildakk_compose.ui.detail.DetailScreen
import com.compose.cocktaildakk_compose.ui.home.HomeScreen
import com.compose.cocktaildakk_compose.ui.mypage.MypageScreen
import com.compose.cocktaildakk_compose.ui.mypage.modify.*
import com.compose.cocktaildakk_compose.ui.onboarding.*
import com.compose.cocktaildakk_compose.ui.search.SearchScreen
import com.compose.cocktaildakk_compose.ui.search.SearchViewModel
import com.compose.cocktaildakk_compose.ui.search.searchResult.SearchResultScreen
import com.compose.cocktaildakk_compose.ui.splash.SplashScreen
import com.compose.cocktaildakk_compose.ui.theme.CocktailDakk_composeTheme
import com.compose.cocktaildakk_compose.ui.theme.Color_Default_Backgounrd
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {


  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      CocktailDakk_composeTheme {
        RootIndex()
      }
    }
  }

}

@Composable
private fun RootIndex() {
  val bottomBarState = rememberSaveable { (mutableStateOf(false)) }
  val navController = rememberNavController()
  val navBackStackEntry by navController.currentBackStackEntryAsState()
  when (navBackStackEntry?.destination?.route) {
    "home", "searchresult", "bookmark", "mypage" -> {
      bottomBarState.value = true
    }
    "search", "splash", "modifybase", "modifylevel", "modifykeyword", "modifynickname", "modifycocktailweight" -> {
      bottomBarState.value = false
    }
  }

  Surface(modifier = Modifier.fillMaxSize(), color = Color.Transparent) {
    RootNavhost(navController, bottomBarState)
  }
}

@Composable
private fun RootNavhost(
  navController: NavHostController,
  bottomBarState: MutableState<Boolean>,
) {
  val bottomNavItems = listOf(
    Screen.Home,
    Screen.SearchResult,
    Screen.Bookmark,
    Screen.Mypage,
  )
  val scaffoldState = rememberScaffoldState()
  val searchViewModel: SearchViewModel = hiltViewModel()

  Scaffold(
    scaffoldState = scaffoldState,
    bottomBar = { ButtomBar(navController, bottomNavItems, bottomBarState) }
  ) { innerPadding ->
    NavHost(
      navController,
      startDestination = "splash",
      Modifier
        .padding(innerPadding)
        .background(color = Color.Transparent)
    ) {
      composable("splash") {
        SplashScreen(
          navController = navController,
          scaffoldState = scaffoldState
        )
      }
      onboardGraph(
        scaffoldState = scaffoldState,
        navController = navController,
      )
      mainGraph(
        scaffoldState = scaffoldState,
        navController = navController,
        searchViewModel = searchViewModel,
      )
      composable("search") {
        SearchScreen(
          navController = navController,
        )
      }
      composable("detail/{idx}",
        arguments = listOf(
          navArgument("idx") {
            type = NavType.IntType
          }
        )) { entry ->
        LaunchedEffect(Unit) {
          bottomBarState.value = false
        }
        DetailScreen(
          navController = navController,
          idx = entry.arguments?.getInt("idx") ?: 0
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
//              popUpTo(navController.graph.findStartDestination().id) {
              popUpTo("MainGraph") {
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

fun NavGraphBuilder.onboardGraph(
  navController: NavController,
  scaffoldState: ScaffoldState,
) {
  navigation(startDestination = "onboard_start", route = "OnboardGraph") {
    composable("onboard_start") { entry ->
      val backStackEntry = remember(entry) {
        navController.getBackStackEntry("OnboardGraph")
      }
      val onboardViewModel: OnboardViewModel = hiltViewModel(backStackEntry)
      OnboardStartScreen(navController, onboardViewModel = onboardViewModel)
    }
    composable("onboard_nickname") {
      val backStackEntry = remember(it) {
        navController.getBackStackEntry("OnboardGraph")
      }
      val onboardViewModel: OnboardViewModel = hiltViewModel(backStackEntry)
      OnboardNicknameScreen(
        navController,
        onboardViewModel = onboardViewModel,
        scaffoldState = scaffoldState
      )
    }
    composable("onboard_age") {
      val backStackEntry = remember(it) {
        navController.getBackStackEntry("OnboardGraph")
      }
      val onboardViewModel: OnboardViewModel = hiltViewModel(backStackEntry)
      OnboardAgeScreen(navController, onboardViewModel = onboardViewModel)
    }
    composable("onboard_sex") {
      val backStackEntry = remember(it) {
        navController.getBackStackEntry("OnboardGraph")
      }
      val onboardViewModel: OnboardViewModel = hiltViewModel(backStackEntry)
      OnboardSexScreen(navController, onboardViewModel = onboardViewModel)
    }
    composable("onboard_level") {
      val backStackEntry = remember(it) {
        navController.getBackStackEntry("OnboardGraph")
      }
      val onboardViewModel: OnboardViewModel = hiltViewModel(backStackEntry)
      OnboardLevelScreen(navController, onboardViewModel = onboardViewModel)
    }
    composable("onboard_base") {
      val backStackEntry = remember(it) {
        navController.getBackStackEntry("OnboardGraph")
      }
      val onboardViewModel: OnboardViewModel = hiltViewModel(backStackEntry)
      OnboardBaseScreen(
        scaffoldState = scaffoldState,
        navController = navController,
        onboardViewModel = onboardViewModel
      )
    }
    composable("onboard_keyword") {
      val backStackEntry = remember(it) {
        navController.getBackStackEntry("OnboardGraph")
      }
      val onboardViewModel: OnboardViewModel = hiltViewModel(backStackEntry)
      OnboardKeywordScreen(
        scaffoldState = scaffoldState,
        navController = navController, onboardViewModel = onboardViewModel
      )
    }
  }
}


fun NavGraphBuilder.mainGraph(
  navController: NavController,
  scaffoldState: ScaffoldState,
  searchViewModel: SearchViewModel,
) {
  navigation(startDestination = Screen.Home.route, route = "MainGraph") {
    composable(Screen.Home.route) { HomeScreen(navController) }
    composable(Screen.SearchResult.route) { entry ->
      LaunchedEffect(Unit) {
      }
      SearchResultScreen(
        navController = navController,
        searchViewModel = searchViewModel,
      )
    }
    composable(Screen.Bookmark.route) {
      BookmarkScreen(
        navController = navController,
        scaffoldState = scaffoldState
      )
    }
    composable(Screen.Mypage.route) { MypageScreen(navController = navController) }
    composable("modifybase") {
      ModifyBaseScreen(
        navController = navController,
        scaffoldState = scaffoldState
      )
    }
    composable("modifykeyword") {
      ModifyKeywordScreen(
        navController = navController,
        scaffoldState = scaffoldState
      )
    }
    composable("modifylevel") {
      ModifyLevelScreen(
        navController = navController,
      )
    }
    composable("modifynickname") {
      ModifyNicknameScreen(
        navController = navController,
        scaffoldState = scaffoldState
      )
    }
    composable("modifycocktailweight") {
      ModifyCocktailWeightScreen(
        navController = navController,
      )
    }
  }
}
