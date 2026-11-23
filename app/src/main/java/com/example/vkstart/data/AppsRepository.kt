package com.example.vkstart.data

import com.example.vkstart.data.api.CatalogApiService
import com.example.vkstart.data.mapper.AppMapper
import com.example.vkstart.domain.model.AppDetails
import com.example.vkstart.domain.model.AppInfo
import com.example.vkstart.domain.repository.AppsRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
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

    override suspend fun getRating(id: String): Float {
        return catalogApiService.getRating(id)
    }

    override suspend fun loadRatingsSequentially(ids: List<String>): Map<String, Float> {
        val map = mutableMapOf<String, Float>()
        for (id in ids) {
            map[id] = getRating(id)
        }
        return map
    }

    override suspend fun loadRatingsInParallel(ids: List<String>): Map<String, Float> = coroutineScope {
        ids.map { id ->
            async { id to getRating(id) }
        }.awaitAll().toMap()
    }
}

object SampleAppsRepository : AppsRepository {
    private val mapper = AppMapper()
    private val dummyApi = object : CatalogApiService {
        override suspend fun getCatalog() = SampleData.sampleApps
        override suspend fun getAppDetails(id: String) = SampleData.detailsFor(id)
    }

    override suspend fun getApps(): List<AppInfo> = SampleData.sampleApps.map(mapper::toDomain)
    override suspend fun getAppDetails(id: String): AppDetails = mapper.toDomain(SampleData.detailsFor(id))
    override suspend fun getRating(id: String): Float = dummyApi.getRating(id)
    override suspend fun loadRatingsSequentially(ids: List<String>): Map<String, Float> {
        val map = mutableMapOf<String, Float>()
        for (id in ids) {
            map[id] = getRating(id)
        }
        return map
    }
    override suspend fun loadRatingsInParallel(ids: List<String>): Map<String, Float> = coroutineScope {
        ids.map { id ->
            async { id to getRating(id) }
        }.awaitAll().toMap()
    }
}
