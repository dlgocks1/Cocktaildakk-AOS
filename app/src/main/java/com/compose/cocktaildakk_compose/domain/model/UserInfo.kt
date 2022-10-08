package com.compose.cocktaildakk_compose.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.compose.cocktaildakk_compose.data.data_source.Converters

@Entity
data class UserInfo(
  var level: Int = 5,
  var sex: String = "Male",
  var age: Int = 20,
  @TypeConverters(Converters::class)
  var keyword: List<String> = listOf(),
  @TypeConverters(Converters::class)
  var base: List<String> = listOf()

) {
  @PrimaryKey(autoGenerate = true)
  var id: Int = 1
}
