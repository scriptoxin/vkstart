package com.example.vkstart.data.mapper

import com.example.vkstart.data.dto.AppInfoDto
import com.example.vkstart.data.dto.AppDetailsDto
import com.example.vkstart.domain.model.AppInfo
import com.example.vkstart.domain.model.AppDetails

object AppMapper {
    fun toDomain(dto: AppInfoDto) = AppInfo(
        id = dto.id, name = dto.name, description = dto.description,
        category = dto.category, iconRes = dto.iconRes,
    )

    fun toDomain(dto: AppDetailsDto) = AppDetails(
        id = dto.id, name = dto.name, developer = dto.developer,
        category = dto.category, ageRating = dto.ageRating, size = dto.size,
        iconRes = dto.iconRes, screenshotUrlList = dto.screenshotUrlList.toList(),
        description = dto.description,
    )
}
