package com.compose.cocktaildakk_compose.network

import com.compose.cocktaildakk_compose.data.response.LocationSearchResponse
import com.compose.cocktaildakk_compose.ui.naverMap.NaverMapViewModel.Companion.SEARCH_RADIUS
import com.skydoves.sandwich.ApiResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface MapService {
    @GET("/v2/local/search/keyword.json?")
    suspend fun locationSearch(
        @Query("query") query: String,
        @Query("y") lat: Double,
        @Query("x") lon: Double,
        @Query("radius") radius: Int = SEARCH_RADIUS
    ): ApiResponse<LocationSearchResponse>

}