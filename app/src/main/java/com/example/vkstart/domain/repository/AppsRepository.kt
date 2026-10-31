package com.example.vkstart.domain.repository

import com.example.vkstart.domain.model.AppInfo
import com.example.vkstart.domain.model.AppDetails

interface AppsRepository {
    fun getApps(): List<AppInfo>
    fun getAppDetails(id: Int): AppDetails
}
