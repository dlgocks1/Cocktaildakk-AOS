package com.compose.cocktaildakk_compose.domain.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CocktailListInfo(
  @Json(name = "id")
  val id: Int? = 0,
  @Json(name = "name")
  val name: String? = ""
)