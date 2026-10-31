package com.example.vkstart.data.dto


/**
 * Модель данных для приложения в списке.
 *
 * @property id Уникальный идентификатор приложения
 * @property name Название приложения
 * @property description Краткое описание приложения
 * @property category Категория приложения
 * @property iconRes Ресурс иконки приложения
 */
data class AppInfoDto(
    val id: Int,
    val name: String,
    val description: String,
    val category: String,
    val iconRes: Int
)
