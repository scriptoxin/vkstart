package com.example.vkstart

import com.example.vkstart.data.SampleData
import org.junit.Assert.assertEquals
import org.junit.Test

class ShowcaseDataTest {
    @Test
    fun showcaseContainsNineDistinctAppsInThreeFullPages() {
        val pages = SampleData.showcasePages
        assertEquals(3, pages.size)
        pages.forEach { assertEquals(3, it.size) }
        assertEquals(9, pages.flatten().map { it.id }.distinct().size)
    }

    @Test
    fun everyListedAppResolvesToItsOwnDetails() {
        SampleData.sampleApps.forEach { app ->
            val details = SampleData.detailsFor(app.id)
            assertEquals(app.id, details.id)
            assertEquals(app.name, details.name)
            assertEquals(app.iconRes, details.iconRes)
        }
    }
}
