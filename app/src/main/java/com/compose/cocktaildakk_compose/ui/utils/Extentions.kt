package com.compose.cocktaildakk_compose.ui.utils

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.compose.material.ScaffoldState
import androidx.core.content.ContextCompat
import androidx.navigation.NavBackStackEntry
import com.compose.cocktaildakk_compose.BITMAP_IMAGES
import com.compose.cocktaildakk_compose.ui.detail.model.CroppingImage

suspend fun ScaffoldState.showSnackbar(message: String) {
    this.snackbarHostState.showSnackbar(message)
}

fun getCroppedImageFromBackStack(navBackStackEntry: NavBackStackEntry?) =
    navBackStackEntry?.savedStateHandle?.getLiveData<List<CroppingImage>>(BITMAP_IMAGES)?.value


fun galleryPermissionCheck(
    context: Context,
    launcher: ManagedActivityResultLauncher<String, Boolean>,
    action: () -> Unit,
) {
    when (PackageManager.PERMISSION_GRANTED) {
        ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.READ_EXTERNAL_STORAGE,
        ),
        -> {
            action()
        }
        else -> {
            launcher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
        }
    }
}
