package com.example.vkstart.domain.repository

import com.example.vkstart.domain.model.AppInfo
import com.example.vkstart.domain.model.AppDetails

interface AppsRepository {
    suspend fun getApps(): List<AppInfo>
    suspend fun getAppDetails(id: String): AppDetails
    suspend fun getRating(id: String): Float
    suspend fun loadRatingsSequentially(ids: List<String>): Map<String, Float>
    suspend fun loadRatingsInParallel(ids: List<String>): Map<String, Float>
}
