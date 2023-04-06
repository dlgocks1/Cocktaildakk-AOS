package com.compose.cocktaildakk_compose.ui.components

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import com.compose.cocktaildakk_compose.ui.Screen
import com.compose.cocktaildakk_compose.ui.domain.model.ApplicationState
import com.compose.cocktaildakk_compose.ui.theme.Color_Default_Backgounrd
import com.compose.cocktaildakk_compose.ui.theme.ScreenRoot


/** BottomNavigation Bar를 정의한다. */
@Composable
fun BottomBar(
    appState: ApplicationState,
    bottomNavItems: List<Screen> = Screen.BOTTOM_NAV_ITEMS,
) {
    AnimatedVisibility(
        visible = appState.bottomBarState.value,
        enter = fadeIn() + expandVertically(),
        exit = fadeOut() + shrinkVertically(),
        modifier = Modifier.background(color = Color_Default_Backgounrd),
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
                                        ?: return@Surface,
                                ),
                                contentDescription = null,
                            )
                        }
                    },
                    label =
                    if (isSelected) {
                        { Text(text = stringResource(screen.stringResId), color = Color.White) }
                    } else {
                        null
                    },
                    selected = isSelected,
                    onClick = {
                        appState.navController.navigate(screen.route) {
                            popUpTo(ScreenRoot.MAIN_GRAPH) {
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
