package com.example.vkstart.domain.usecase

import com.example.vkstart.domain.model.AppDetails
import com.example.vkstart.domain.model.AppInfo
import com.example.vkstart.domain.repository.AppsRepository

class FakeAppsRepository : AppsRepository {
    var appsList: List<AppInfo> = listOf(
        AppInfo("1", "App 1", "Desc 1", "Social", "url1", 1),
        AppInfo("2", "App 2", "Desc 2", "Games", "url2", 2),
        AppInfo("3", "App 3", "Desc 3", "Social", "url3", 3),
        AppInfo("4", "App 4", "Desc 4", "Tools", "url4", 4),
        AppInfo("5", "App 5", "Desc 5", "Games", "url5", 5),
    )

    var detailsMap: MutableMap<String, AppDetails> = mutableMapOf(
        "1" to AppDetails("1", "App 1", "Dev 1", "Social", 12, 10.0f, "url1", 1, listOf("s1"), "Desc 1"),
        "2" to AppDetails("2", "App 2", "Dev 2", "Games", 16, 20.0f, "url2", 2, listOf("s2"), "Desc 2"),
    )

    var ratingToReturn: Float = 4.0f
    var sequentialCalledWith: List<String>? = null
    var parallelCalledWith: List<String>? = null
    var shouldThrowException: Boolean = false

    override suspend fun getApps(): List<AppInfo> {
        if (shouldThrowException) throw RuntimeException("Error fetching apps")
        return appsList
    }

    override suspend fun getAppDetails(id: String): AppDetails {
        if (shouldThrowException) throw RuntimeException("Error fetching details")
        return detailsMap[id] ?: throw NoSuchElementException("App not found: $id")
    }

    override suspend fun getRating(id: String): Float {
        if (shouldThrowException) throw RuntimeException("Error fetching rating")
        return ratingToReturn
    }

    override suspend fun loadRatingsSequentially(ids: List<String>): Map<String, Float> {
        if (shouldThrowException) throw RuntimeException("Error loading ratings")
        sequentialCalledWith = ids
        return ids.associateWith { ratingToReturn }
    }

    override suspend fun loadRatingsInParallel(ids: List<String>): Map<String, Float> {
        if (shouldThrowException) throw RuntimeException("Error loading ratings")
        parallelCalledWith = ids
        return ids.associateWith { ratingToReturn }
    }
}
