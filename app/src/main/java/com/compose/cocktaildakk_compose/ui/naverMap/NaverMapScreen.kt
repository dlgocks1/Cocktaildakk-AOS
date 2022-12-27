@file:OptIn(ExperimentalNaverMapApi::class)

package com.compose.cocktaildakk_compose.ui.naverMap

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.compose.cocktaildakk_compose.ui.ApplicationState
import com.compose.cocktaildakk_compose.ui.theme.Color_White_70
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraPosition
import com.naver.maps.map.CameraUpdate
import com.naver.maps.map.compose.*

@Composable
fun NaverMapScreen(appState: ApplicationState) {
    var mapProperties by remember {
        mutableStateOf(
            MapProperties(maxZoom = 10.0, minZoom = 5.0)
        )
    }
    var mapUiSettings by remember {
        mutableStateOf(
            MapUiSettings(isLocationButtonEnabled = false)
        )
    }

    val seoul = LatLng(37.532600, 127.024612)
    val cameraPositionState: CameraPositionState = rememberCameraPositionState {
        // 카메라 초기 위치를 설정합니다.
        position = CameraPosition(seoul, 11.0)
    }
    Box(Modifier.fillMaxSize()) {
        // 관련 내용 https://github.com/fornewid/naver-map-compose
        NaverMap(
            properties = mapProperties,
            cameraPositionState = cameraPositionState,
            uiSettings = mapUiSettings
        ) {
            CircleOverlay(
                center = LatLng(37.532600, 127.024612),
                color = Color.Black,
                radius = 20000.0
            )

//            Marker(
//                state = MarkerState(position = LatLng(37.532600, 127.024612)),
//                captionText = "Marker in Seoul"
//            )
            Marker(
                state = MarkerState(position = LatLng(37.390791, 127.096306)),
                captionText = "Marker in Pangyo"
            )
        }
        Column {
            Button(onClick = {
                mapProperties = mapProperties.copy(
                    isBuildingLayerGroupEnabled = !mapProperties.isBuildingLayerGroupEnabled
                )
            }) {
                Text(text = "Toggle isBuildingLayerGroupEnabled")
            }
            Button(onClick = {
                mapUiSettings = mapUiSettings.copy(
                    isLocationButtonEnabled = !mapUiSettings.isLocationButtonEnabled
                )
            }) {
                Text(text = "Toggle isLocationButtonEnabled")
            }
            Button(onClick = {
                // 카메라를 새로운 줌 레벨로 이동합니다.
                cameraPositionState.move(CameraUpdate.zoomIn())
            }) {
                Text(text = "Zoom In")
            }

        }
    }
}