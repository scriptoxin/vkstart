package com.example.vkstart.data.mapper

import com.example.vkstart.data.dto.AppDetailsDto
import com.example.vkstart.data.dto.AppInfoDto
import com.example.vkstart.domain.model.AppDetails
import com.example.vkstart.domain.model.AppInfo
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class AppMapperTest {

    private val mapper = AppMapper()

    @Test
    fun toDomain_appInfoDto_mapsAllFieldsCorrectly() {
        val dto = AppInfoDto(
            id = "10",
            name = "VK Music",
            description = "Music app",
            category = "Music",
            iconUrl = "http://example.com/icon.png",
            iconRes = 101,
        )

        val domain = mapper.toDomain(dto)

        assertEquals("10", domain.id)
        assertEquals("VK Music", domain.name)
        assertEquals("Music app", domain.description)
        assertEquals("Music", domain.category)
        assertEquals("http://example.com/icon.png", domain.iconUrl)
        assertEquals(101, domain.iconRes)
        assertNull(domain.rating)
    }

    @Test
    fun toDomain_appInfoDtoWithNullIconUrl_mapsToEmptyString() {
        val dto = AppInfoDto(
            id = "11",
            name = "App without icon",
            description = "Desc",
            category = "Tools",
            iconUrl = null,
            iconRes = 0,
        )

        val domain = mapper.toDomain(dto)

        assertEquals("", domain.iconUrl)
    }

    @Test
    fun toDomain_appDetailsDto_mapsAllFieldsCorrectly() {
        val screenshots = mutableListOf("sc1.png", "sc2.png")
        val dto = AppDetailsDto(
            id = "20",
            name = "VK Messenger",
            developer = "VK",
            category = "Social",
            ageRating = 12,
            size = 45.5f,
            iconUrl = "http://example.com/msg.png",
            iconRes = 202,
            screenshotUrlList = screenshots,
            description = "Messenger app",
        )

        val domain = mapper.toDomain(dto)

        assertEquals("20", domain.id)
        assertEquals("VK Messenger", domain.name)
        assertEquals("VK", domain.developer)
        assertEquals("Social", domain.category)
        assertEquals(12, domain.ageRating)
        assertEquals(45.5f, domain.size)
        assertEquals("http://example.com/msg.png", domain.iconUrl)
        assertEquals(202, domain.iconRes)
        assertEquals(listOf("sc1.png", "sc2.png"), domain.screenshotUrlList)
        assertEquals("Messenger app", domain.description)
    }

    @Test
    fun toDto_appInfo_mapsAllFieldsCorrectly() {
        val domain = AppInfo(
            id = "30",
            name = "Sferum",
            description = "Educational app",
            category = "Education",
            iconUrl = "http://example.com/sferum.png",
            iconRes = 303,
            rating = 4.8f,
        )

        val dto = mapper.toDto(domain)

        assertEquals("30", dto.id)
        assertEquals("Sferum", dto.name)
        assertEquals("Educational app", dto.description)
        assertEquals("Education", dto.category)
        assertEquals("http://example.com/sferum.png", dto.iconUrl)
        assertEquals(303, dto.iconRes)
    }

    @Test
    fun toDto_appInfoWithEmptyIconUrl_mapsToNull() {
        val domain = AppInfo(
            id = "31",
            name = "No Icon App",
            description = "Desc",
            category = "Other",
            iconUrl = "",
            iconRes = 0,
        )

        val dto = mapper.toDto(domain)

        assertNull(dto.iconUrl)
    }

    @Test
    fun toDto_appDetails_mapsAllFieldsCorrectly() {
        val domain = AppDetails(
            id = "40",
            name = "VK Play",
            developer = "VK Games",
            category = "Games",
            ageRating = 16,
            size = 120.0f,
            iconUrl = "http://example.com/play.png",
            iconRes = 404,
            screenshotUrlList = listOf("shot1.png", "shot2.png"),
            description = "Gaming platform",
        )

        val dto = mapper.toDto(domain)

        assertEquals("40", dto.id)
        assertEquals("VK Play", dto.name)
        assertEquals("VK Games", dto.developer)
        assertEquals("Games", dto.category)
        assertEquals(16, dto.ageRating)
        assertEquals(120.0f, dto.size)
        assertEquals("http://example.com/play.png", dto.iconUrl)
        assertEquals(404, dto.iconRes)
        assertEquals(listOf("shot1.png", "shot2.png"), dto.screenshotUrlList)
        assertEquals("Gaming platform", dto.description)
    }

    @Test
    fun toDomain_appDetailsDto_createsDefensiveCopyOfScreenshots() {
        val screenshots = mutableListOf("original.png")
        val dto = AppDetailsDto(
            id = "50",
            name = "App",
            developer = "Dev",
            category = "Cat",
            ageRating = 0,
            size = 1.0f,
            iconUrl = null,
            iconRes = 0,
            screenshotUrlList = screenshots,
            description = "Desc",
        )

        val domain = mapper.toDomain(dto)
        screenshots.add("modified.png")

        assertEquals(listOf("original.png"), domain.screenshotUrlList)
    }
}
