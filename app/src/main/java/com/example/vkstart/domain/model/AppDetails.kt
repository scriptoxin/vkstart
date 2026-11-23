package com.example.vkstart.domain.model

data class AppDetails(
    val id: String,
    val name: String,
    val developer: String,
    val category: String,
    val ageRating: Int,
    val size: Float,
    val iconUrl: String = "",
    val iconRes: Int = 0,
    val screenshotUrlList: List<String> = emptyList(),
    val description: String,
)
