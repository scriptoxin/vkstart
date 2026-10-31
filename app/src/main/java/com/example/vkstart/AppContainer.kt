package com.example.vkstart

import com.example.vkstart.data.SampleAppsRepository
import com.example.vkstart.domain.repository.AppsRepository

object AppContainer {
    val appsRepository: AppsRepository = SampleAppsRepository
}
