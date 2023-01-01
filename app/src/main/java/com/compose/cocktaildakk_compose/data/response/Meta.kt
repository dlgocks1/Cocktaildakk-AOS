package com.compose.cocktaildakk_compose.data.response


import com.google.gson.annotations.SerializedName
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Meta(
    @SerializedName("is_end")
    val isEnd: Boolean,
    @SerializedName("pageable_count")
    val pageableCount: Int,
    @SerializedName("same_name")
    val sameName: SameName,
    @SerializedName("total_count")
    val totalCount: Int
)