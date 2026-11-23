package com.example.vkstart.data.api

import com.example.vkstart.data.dto.AppDetailsDto
import com.example.vkstart.data.dto.AppInfoDto
import kotlinx.coroutines.delay
import retrofit2.http.GET
import retrofit2.http.Path

interface CatalogApiService {
    @GET("catalog")
    suspend fun getCatalog(): List<AppInfoDto>

    @GET("catalog/{id}")
    suspend fun getAppDetails(@Path("id") id: String): AppDetailsDto

    suspend fun getRating(id: String): Float {
        delay(1500)
        return (30..50).random() / 10f
    }
}
