package com.compose.cocktaildakk_compose.domain.model


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@Entity
@JsonClass(generateAdapter = true)
data class Cocktail(
  @Json(name = "base")
  val base: String = "",
  @Json(name = "enName")
  val enName: String = "",
  @Json(name = "explain")
  val explain: String = "",
  @PrimaryKey(autoGenerate = false)
  @Json(name = "idx")
  val idx: Int = 0,
  @Json(name = "ingredient")
  val ingredient: String = "",
  @Json(name = "keyword")
  val keyword: String = "",
  @Json(name = "krName")
  val krName: String = "",
  @Json(name = "level")
  val level: Int = 0,
  @Json(name = "mix")
  val mix: String = "",
  @Json(name = "imgUrl")
  val imgUrl: String = "",
  var isBookmark: Boolean = false
)