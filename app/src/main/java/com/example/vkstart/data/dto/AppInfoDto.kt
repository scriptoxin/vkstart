package com.example.vkstart.data.dto

import com.google.gson.annotations.SerializedName

/**
 * Модель данных для приложения в списке.
 *
 * @property id Уникальный идентификатор приложения
 * @property name Название приложения
 * @property description Краткое описание приложения
 * @property category Категория приложения
 * @property iconUrl URL иконки приложения
 * @property iconRes Ресурс иконки приложения
 */
data class AppInfoDto(
    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String,
    @SerializedName("description") val description: String,
    @SerializedName("category") val category: String,
    @SerializedName("iconUrl") val iconUrl: String? = null,
    val iconRes: Int = 0,
)
