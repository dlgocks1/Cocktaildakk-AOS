package com.compose.cocktaildakk_compose.ui.domain.model

import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.Stable
import androidx.navigation.NavHostController
import com.compose.cocktaildakk_compose.ui.utils.showSnackbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Stable
class ApplicationState(
    val bottomBarState: MutableState<Boolean>,
    val navController: NavHostController,
    val scaffoldState: ScaffoldState,
    val coroutineScope: CoroutineScope,
) {
    fun showSnackbar(message: String) {
        coroutineScope.launch {
            scaffoldState.showSnackbar(message)
        }
    }
}
