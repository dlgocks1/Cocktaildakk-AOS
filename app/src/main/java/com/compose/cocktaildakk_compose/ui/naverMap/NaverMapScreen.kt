@file:OptIn(ExperimentalNaverMapApi::class)

package com.compose.cocktaildakk_compose.ui.naverMap

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.compose.cocktaildakk_compose.R
import com.compose.cocktaildakk_compose.data.response.Marker
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
    var detailVisibility by remember {
        mutableStateOf(false)
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
                    width = if (naverMapViewModel.selectedMarker.value == marker) 50.dp else 0.dp,
                    height = if (naverMapViewModel.selectedMarker.value == marker) 50.dp else 0.dp,
                    captionText = marker.placeName,
                    iconTintColor = Color_Default_Backgounrd,
                    icon = OverlayImage.fromResource(R.drawable.ic_baseline_wine_bar_24),
                    onClick = {
                        naverMapViewModel.setSelectedMarker(marker)
                        detailVisibility = true
                        false
                    }
                )
            }
        }
        Row(
            Modifier
                .align(Alignment.BottomEnd)
                .padding(20.dp)
                .clip(RoundedCornerShape(20.dp))
                .background(Color_Default_Backgounrd)
                .clickable {
                    userPosition = cameraPositionState.position.target
                }) {
            Row(
                Modifier.padding(20.dp, 5.dp, 10.dp, 5.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "현재 지도에서 검색", color = Color.White, fontSize = 16.sp)
                Spacer(modifier = Modifier.width(10.dp))
                Icon(
                    painter = painterResource(id = R.drawable.ic_baseline_location_searching_24),
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier
                        .size(24.dp)
                )
            }
        }


        AnimatedVisibility(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .fillMaxWidth(1f),
            visible = detailVisibility,
            enter = fadeIn() + expandVertically(),
            exit = fadeOut() + shrinkVertically(),
        ) {
            LocationDetail(naverMapViewModel.selectedMarker.value) {
                naverMapViewModel.setSelectedMarker(null)
                detailVisibility = false
            }
        }

    }
}

@Composable
private fun LocationDetail(marker: Marker?, changeVisibility: () -> Unit) {

    val context = LocalContext.current

    Column(
        Modifier
            .fillMaxWidth(1f)
            .clip(RoundedCornerShape(0.dp, 0.dp, 20.dp, 20.dp))
            .background(Color_Default_Backgounrd)
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.padding(20.dp)
        ) {
            Column(Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(5.dp)) {
                Text(
                    text = marker?.placeName ?: "-",
                    color = Color.White,
                    fontSize = 22.sp,
                    modifier = Modifier.clickable {
                        val browserIntent = Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse(marker?.placeUrl)
                        )
                        context.startActivity(browserIntent)
                    })
                Text(
                    text = marker?.categoryName ?: "-",
                    fontSize = 14.sp,
                    color = Color.White.copy(alpha = 0.7f)
                )
                Text(text = marker?.addressName ?: "-", color = Color.White.copy(alpha = 0.7f))
                Text(text = marker?.phone ?: "-", color = Color.White.copy(alpha = 0.7f))
            }
            Row(horizontalArrangement = Arrangement.End, verticalAlignment = Alignment.Bottom) {
                Box(
                    Modifier
                        .size(44.dp)
                        .clip(CircleShape)
                        .background(Color_Default_Backgounrd)
                        .border(BorderStroke(1.dp, Color.White), CircleShape)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_baseline_call_24),
                        contentDescription = "Phone Icon",
                        tint = Color.White,
                        modifier = Modifier
                            .size(26.dp)
                            .align(Alignment.Center)
                            .clickable {
                                val intent = Intent(
                                    Intent.ACTION_DIAL,
                                    Uri.fromParts("tel", marker?.phone, null)
                                )
                                context.startActivity(intent)
                            }
                    )
                }
                Spacer(modifier = Modifier.width(20.dp))
                Box(
                    Modifier
                        .size(44.dp)
                        .clip(CircleShape)
                        .background(Color_Default_Backgounrd)
                        .border(BorderStroke(1.dp, Color.White), CircleShape)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_baseline_navigation_24),
                        contentDescription = "Naivagetion Icon",
                        tint = Color.White,
                        modifier = Modifier
                            .size(26.dp)
                            .align(Alignment.Center)
                            .clickable {
                                val url =
                                    "nmap://route/public?dlat=${marker?.y}&dlng=${marker?.x}&dname=${marker?.placeName}&appname=cocktaildakk"
                                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                                intent.addCategory(Intent.CATEGORY_BROWSABLE)

                                val list: List<ResolveInfo> =
                                    context.packageManager.queryIntentActivities(
                                        intent,
                                        PackageManager.MATCH_DEFAULT_ONLY
                                    )
                                if (list.isEmpty()) {
                                    context.startActivity(
                                        Intent(
                                            Intent.ACTION_VIEW,
                                            Uri.parse("market://details?id=com.nhn.android.nmap")
                                        )
                                    )
                                } else {
                                    context.startActivity(intent)
                                }
                            }
                    )
                }
            }

        }

        Box(modifier = Modifier.fillMaxWidth()) {
            Spacer(
                modifier = Modifier
                    .align(Alignment.Center)
                    .height(5.dp)
                    .fillMaxWidth(0.1f)
                    .background(color = Color(0x50ffffff))
                    .clickable {
                        changeVisibility()
                    }
            )
        }


        Spacer(modifier = Modifier.height(10.dp))

    }
}

@Preview
@Composable
fun LocationDetailPreview() {
//    LocationDetail()
}