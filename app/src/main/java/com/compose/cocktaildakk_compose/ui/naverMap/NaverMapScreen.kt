@file:OptIn(ExperimentalNaverMapApi::class)

package com.compose.cocktaildakk_compose.ui.naverMap

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.compose.cocktaildakk_compose.R
import com.compose.cocktaildakk_compose.ui.ApplicationState
import com.compose.cocktaildakk_compose.ui.theme.Color_Default_Backgounrd
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraPosition
import com.naver.maps.map.CameraUpdate
import com.naver.maps.map.compose.*
import com.naver.maps.map.overlay.OverlayImage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun NaverMapScreen(
    appState: ApplicationState,
) {
    val context = LocalContext.current
    var granted by remember {
        mutableStateOf(false)
    }
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            granted = isGranted
        }
    )
    if (ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    ) {
        granted = true
    }
    if (granted) {
        NaverMap(appState)
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color_Default_Backgounrd),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_baseline_sentiment_very_dissatisfied_24),
                contentDescription = null,
                modifier = Modifier.size(24.dp),
                tint = Color.White
            )
            Spacer(modifier = Modifier.height(10.dp))
            Button(onClick = {
                launcher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
            }) {
                Text(text = "위치 권한을 허용해 주세요.", color = Color.White)
            }
        }
    }
}


// 네이버 컴포즈 관련 내용 https://github.com/fornewid/naver-map-compose
@Composable
private fun NaverMap(
    appState: ApplicationState,
    naverMapViewModel: NaverMapViewModel = hiltViewModel()
) {
    var mapProperties by remember {
        mutableStateOf(
            MapProperties(maxZoom = 5.0, minZoom = 11.0)
        )
    }
    var mapUiSettings by remember {
        mutableStateOf(
            MapUiSettings(isLocationButtonEnabled = false)
        )
    }

    val scope = rememberCoroutineScope()
    val seoul = LatLng(37.532600, 127.024612)
    val cameraPositionState: CameraPositionState = rememberCameraPositionState {
        position = CameraPosition(seoul, 8.0)
    }
    var userPosition by remember {
        mutableStateOf(seoul)
    }

    LaunchedEffect(naverMapViewModel.location.value) {
        naverMapViewModel.location.value?.let {
            userPosition = LatLng(
                naverMapViewModel.location.value!!.latitude,
                naverMapViewModel.location.value!!.longitude
            )
        }
    }

    LaunchedEffect(userPosition) {
        cameraPositionState.move(
            CameraUpdate.scrollTo(userPosition)
        )
        cameraPositionState.move(
            CameraUpdate.zoomTo(13.0)
        )
        launch(Dispatchers.IO) {
            naverMapViewModel.getMarkers(userPosition) {
                scope.launch {
                    appState.showSnackbar("반경 3Km 이내에 바가 없습니다.")
                }
            }
        }
    }

    LaunchedEffect(Unit) {
        naverMapViewModel.addLocationListener()
    }

    DisposableEffect(Unit) {
        onDispose {
            naverMapViewModel.removeLocationListener()
        }
    }

    Box(Modifier.fillMaxSize()) {
        NaverMap(
            properties = mapProperties,
            cameraPositionState = cameraPositionState,
            uiSettings = mapUiSettings
        ) {
            CircleOverlay(
                center = userPosition,
                color = Color_Default_Backgounrd.copy(alpha = 0.3f),
                radius = naverMapViewModel.circleRadius.value
            )
            naverMapViewModel.marker.map { marker ->
                Marker(
                    state = MarkerState(
                        position = LatLng(
                            marker.y.toDouble(),
                            marker.x.toDouble()
                        )
                    ),
                    captionText = marker.placeName,
                    iconTintColor = Color_Default_Backgounrd,
                    icon = OverlayImage.fromResource(R.drawable.ic_baseline_wine_bar_24),
                    onClick = {
                        scope.launch {
                            appState.showSnackbar(marker.addressName.toString())
                        }
                        false
                    }
                )
            }
        }
        Box(
            Modifier
                .align(Alignment.BottomEnd)
                .padding(20.dp)
                .size(54.dp)
                .clip(CircleShape)
                .background(Color_Default_Backgounrd)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_baseline_location_searching_24),
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier
                    .size(36.dp)
                    .align(Alignment.Center)
                    .clickable {
                        userPosition = cameraPositionState.position.target
                    }
            )
        }

//            Button(onClick = {
//                mapProperties = mapProperties.copy(
//                    isBuildingLayerGroupEnabled = !mapProperties.isBuildingLayerGroupEnabled
//                )
//            }) {
//                Text(text = "Toggle isBuildingLayerGroupEnabled")
//            }
//            Button(onClick = {
//                mapUiSettings = mapUiSettings.copy(
//                    isLocationButtonEnabled = !mapUiSettings.isLocationButtonEnabled
//                )
//            }) {
//                Text(text = "Toggle isLocationButtonEnabled")
//            }
//            Button(onClick = {
//                // 카메라를 새로운 줌 레벨로 이동합니다.
//                cameraPositionState.move(CameraUpdate.zoomIn())
//            }) {
//                Text(text = "Zoom In")
//            }

    }
}