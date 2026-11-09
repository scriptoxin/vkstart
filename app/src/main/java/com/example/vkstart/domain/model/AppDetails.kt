package com.example.vkstart.domain.model


// Local-data adaptation of VkEducation/domain/appdetails/AppDetails.kt.
data class AppDetails(
    val id: Int,
    val name: String,
    val developer: String,
    val category: String,
    val ageRating: Int,
    val size: Float,
    val iconRes: Int,
    val screenshotUrlList: List<String>,
    val description: String,
)
