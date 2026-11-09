package com.example.vkstart.data

import com.example.vkstart.data.mapper.AppMapper
import com.example.vkstart.domain.model.AppDetails
import com.example.vkstart.domain.model.AppInfo
import com.example.vkstart.domain.repository.AppsRepository
import javax.inject.Inject

class AppsRepositoryImpl @Inject constructor(
    private val mapper: AppMapper,
) : AppsRepository {
    override fun getApps(): List<AppInfo> = SampleData.sampleApps.map(mapper::toDomain)
    override fun getAppDetails(id: Int): AppDetails = mapper.toDomain(SampleData.detailsFor(id))
}

object SampleAppsRepository : AppsRepository by AppsRepositoryImpl(AppMapper())
