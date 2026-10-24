package com.example.vkstart.data

import com.example.vkstart.model.AppInfo

fun interface AppsRepository {
    fun getApps(): List<AppInfo>
}

object SampleAppsRepository : AppsRepository {
    override fun getApps(): List<AppInfo> = SampleData.sampleApps
}
