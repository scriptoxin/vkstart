package com.example.vkstart

import com.example.vkstart.data.dto.AppDetailsDto
import com.example.vkstart.data.dto.AppInfoDto
import com.example.vkstart.data.mapper.AppMapper
import com.example.vkstart.domain.model.AppDetails
import com.example.vkstart.domain.model.AppInfo
import org.junit.Assert.assertEquals
import org.junit.Test

class AppMapperTest {
    private val mapper = AppMapper()

    @Test
    fun mapsEveryListField() {
        assertEquals(
            AppInfo("42", "Name", "Description", "Category", "url", 123),
            mapper.toDomain(AppInfoDto("42", "Name", "Description", "Category", "url", 123)),
        )
    }

    @Test
    fun mapsEveryDetailsFieldAndCopiesScreenshots() {
        val screenshots = mutableListOf("first", "second")
        val result = mapper.toDomain(
            AppDetailsDto("42", "Name", "Developer", "Category", 16, 123.5f, "url", 123, screenshots, "Description"),
        )
        screenshots.clear()
        assertEquals(
            AppDetails("42", "Name", "Developer", "Category", 16, 123.5f, "url", 123, listOf("first", "second"), "Description"),
            result,
        )
    }
}
