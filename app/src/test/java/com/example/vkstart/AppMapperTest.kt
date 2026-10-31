package com.example.vkstart

import com.example.vkstart.data.dto.AppInfoDto
import com.example.vkstart.data.dto.AppDetailsDto
import com.example.vkstart.data.mapper.AppMapper
import com.example.vkstart.domain.model.AppInfo
import com.example.vkstart.domain.model.AppDetails
import org.junit.Assert.assertEquals
import org.junit.Test

class AppMapperTest {
    @Test
    fun mapsEveryListField() {
        assertEquals(
            AppInfo(42, "Name", "Description", "Category", 123),
            AppMapper.toDomain(AppInfoDto(42, "Name", "Description", "Category", 123)),
        )
    }

    @Test
    fun mapsEveryDetailsFieldAndCopiesScreenshots() {
        val screenshots = mutableListOf("first", "second")
        val result = AppMapper.toDomain(
            AppDetailsDto(42, "Name", "Developer", "Category", 16, 123.5f, 123, screenshots, "Description"),
        )
        screenshots.clear()
        assertEquals(
            AppDetails(42, "Name", "Developer", "Category", 16, 123.5f, 123, listOf("first", "second"), "Description"),
            result,
        )
    }
}
