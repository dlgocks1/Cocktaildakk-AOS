package com.compose.cocktaildakk_compose.data.repository

import android.util.Log
import com.compose.cocktaildakk_compose.data.response.LocationSearchResponse
import com.compose.cocktaildakk_compose.di.NetworkModule
import com.compose.cocktaildakk_compose.domain.repository.MapRepository
import com.compose.cocktaildakk_compose.network.MapService
import com.skydoves.sandwich.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class MapRepositoryImpl @Inject constructor(
    private val mapService: MapService
) : MapRepository {

    override suspend fun getCocktailBars(lat: Double, lon: Double) =
        flow {
            mapService.locationSearch(
                query = "칵테일",
                lat = lat,
                lon = lon,
            ).suspendOnSuccess {
                emit(data)
            }.onError {
                Log.e("[ERROR] ${this::class.java.simpleName}", message())
            }.onFailure {
                Log.e("[ERROR] ${this::class.java.simpleName}", message())
            }.onException {
                Log.e("[ERROR] ${this::class.java.simpleName}", message())
            }
        }.onStart { }.onCompletion { }.flowOn(Dispatchers.IO)


}