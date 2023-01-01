package com.compose.cocktaildakk_compose.domain.repository

import com.compose.cocktaildakk_compose.data.response.LocationSearchResponse
import kotlinx.coroutines.flow.Flow

interface MapRepository {
    fun getCocktailBars(
        lat: Double,
        lon: Double,
    ): Flow<LocationSearchResponse>
}
