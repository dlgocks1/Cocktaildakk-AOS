package com.compose.cocktaildakk_compose.domain.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CocktailVersion(
  @Json(name = "version")
  val version: Float = 0f,
)