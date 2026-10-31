package com.example.vkstart.data

import com.example.vkstart.domain.repository.AppsRepository
import com.example.vkstart.domain.model.AppInfo
import com.example.vkstart.domain.model.AppDetails
import com.example.vkstart.data.mapper.AppMapper

object SampleAppsRepository : AppsRepository {
    override fun getApps(): List<AppInfo> = SampleData.sampleApps.map(AppMapper::toDomain)
    override fun getAppDetails(id: Int): AppDetails = AppMapper.toDomain(SampleData.detailsFor(id))
}
