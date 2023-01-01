package com.compose.cocktaildakk_compose.ui.naverMap

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.os.Looper
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.compose.cocktaildakk_compose.data.response.Marker
import com.compose.cocktaildakk_compose.domain.repository.MapRepository
import com.google.android.gms.location.*
import com.naver.maps.geometry.LatLng
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NaverMapViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val mapRepository: MapRepository,
) : ViewModel(), LifecycleObserver {

    private val fusedLocationClient: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(context)

    private val locationRequest: LocationRequest
    private val myLocationCallback: MyLocationCallback

    private val _location = mutableStateOf<Location?>(null)
    val location: State<Location?> = _location

    private val _markers = mutableStateListOf<Marker>()
    val marker get() = _markers

    private val _circleRadius = mutableStateOf(3000.0)
    val circleRadius: State<Double> = _circleRadius

    private val _selectedMarker = mutableStateOf<Marker?>(null)
    val selectedMarker: State<Marker?> = _selectedMarker

    init {
        locationRequest =
            LocationRequest.Builder(Long.MAX_VALUE) // 초기 1회만 가져옴
                .setPriority(Priority.PRIORITY_HIGH_ACCURACY)
                .setMinUpdateIntervalMillis(Long.MAX_VALUE)
                .build()
        myLocationCallback = MyLocationCallback()
    }

    fun setSelectedMarker(marker: Marker?) {
        _selectedMarker.value = marker
    }

    @SuppressLint("MissingPermission")
    fun addLocationListener() {
        fusedLocationClient.requestLocationUpdates(
            locationRequest,
            myLocationCallback,
            Looper.getMainLooper(),
        )
    }

    fun removeLocationListener() {
        fusedLocationClient.removeLocationUpdates(myLocationCallback)
    }

    fun getMarkers(userPosition: LatLng, onEmpty: () -> Unit) = viewModelScope.launch {
        mapRepository.getCocktailBars(
            lat = userPosition.latitude,
            lon = userPosition.longitude,
        ).collectLatest { response ->
            _markers.clear()
            _markers.addAll(response.markers)

            _circleRadius.value =
                if (response.markers.isEmpty()) {
                    onEmpty()
                    SEARCH_RADIUS.toDouble()
                } else {
                    response.markers.maxOf {
                        userPosition.distanceTo(LatLng(it.y.toDouble(), it.x.toDouble()))
                    }
                }
        }
    }

    inner class MyLocationCallback : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            super.onLocationResult(locationResult)
            val location = locationResult.lastLocation
            _location.value = location
        }
    }

    companion object {
        const val SEARCH_RADIUS = 3000
    }
}
