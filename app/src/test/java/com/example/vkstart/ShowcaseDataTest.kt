package com.example.vkstart

import com.example.vkstart.data.SampleAppsRepository
import org.junit.Assert.assertEquals
import org.junit.Test

class ShowcaseDataTest {
    @Test
    fun showcaseContainsNineDistinctAppsInThreeFullPages() {
        val pages = SampleAppsRepository.getApps().chunked(3)
        assertEquals(3, pages.size)
        pages.forEach { assertEquals(3, it.size) }
        assertEquals(9, pages.flatten().map { it.id }.distinct().size)
    }

    @Test
    fun everyListedAppResolvesToItsOwnDetails() {
        SampleAppsRepository.getApps().forEach { app ->
            val details = SampleAppsRepository.getAppDetails(app.id)
            assertEquals(app.id, details.id)
            assertEquals(app.name, details.name)
            assertEquals(app.iconRes, details.iconRes)
        }
    }
}
