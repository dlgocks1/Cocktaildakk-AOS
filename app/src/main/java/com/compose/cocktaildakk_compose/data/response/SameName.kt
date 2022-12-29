package com.compose.cocktaildakk_compose.data.response


import com.google.gson.annotations.SerializedName
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SameName(
    @SerializedName("keyword")
    val keyword: String,
    @SerializedName("region")
    val region: List<Any?>?,
    @SerializedName("selected_region")
    val selectedRegion: String?
)