package com.compose.cocktaildakk_compose.ui.domain

import androidx.compose.material.ScaffoldState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.*
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.compose.cocktaildakk_compose.ui.domain.model.ApplicationState
import kotlinx.coroutines.CoroutineScope

@Composable
fun rememberApplicationState(
    bottomBarState: MutableState<Boolean> = mutableStateOf(false),
    navController: NavHostController = rememberNavController(),
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
) = remember(bottomBarState, navController) {
    ApplicationState(
        bottomBarState,
        navController,
        scaffoldState,
        coroutineScope
    )
}

