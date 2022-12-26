package com.compose.cocktaildakk_compose.ui.utils

import androidx.compose.material.ScaffoldState

suspend fun ScaffoldState.showSnackbar(message: String) {
    this.snackbarHostState.showSnackbar(message)
}