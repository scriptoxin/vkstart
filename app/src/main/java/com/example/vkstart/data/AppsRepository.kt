package com.example.vkstart.data

import com.example.vkstart.data.api.CatalogApiService
import com.example.vkstart.data.mapper.AppMapper
import com.example.vkstart.domain.model.AppDetails
import com.example.vkstart.domain.model.AppInfo
import com.example.vkstart.domain.repository.AppsRepository
import javax.inject.Inject

class AppsRepositoryImpl @Inject constructor(
    private val catalogApiService: CatalogApiService,
    private val mapper: AppMapper,
) : AppsRepository {
    override suspend fun getApps(): List<AppInfo> {
        return try {
            catalogApiService.getCatalog().map(mapper::toDomain)
        } catch (e: Exception) {
            SampleData.sampleApps.map(mapper::toDomain)
        }
    }

    override suspend fun getAppDetails(id: String): AppDetails {
        return try {
            mapper.toDomain(catalogApiService.getAppDetails(id))
        } catch (e: Exception) {
            mapper.toDomain(SampleData.detailsFor(id))
        }
    }
}

object SampleAppsRepository : AppsRepository {
    private val mapper = AppMapper()
    override suspend fun getApps(): List<AppInfo> = SampleData.sampleApps.map(mapper::toDomain)
    override suspend fun getAppDetails(id: String): AppDetails = mapper.toDomain(SampleData.detailsFor(id))
}
