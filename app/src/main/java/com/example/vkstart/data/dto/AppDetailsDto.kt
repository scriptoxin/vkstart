package com.example.vkstart.data.dto

import com.google.gson.annotations.SerializedName

data class AppDetailsDto(
    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String,
    @SerializedName("developer") val developer: String,
    @SerializedName("category") val category: String,
    @SerializedName("ageRating") val ageRating: Int,
    @SerializedName("size") val size: Float,
    @SerializedName("iconUrl") val iconUrl: String? = null,
    val iconRes: Int = 0,
    @SerializedName("screenshotUrlList") val screenshotUrlList: List<String> = emptyList(),
    @SerializedName("description") val description: String,
)
