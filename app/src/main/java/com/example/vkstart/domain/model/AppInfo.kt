package com.example.vkstart.domain.model


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
data class AppInfo(
    val id: String,
    val name: String,
    val description: String,
    val category: String,
    val iconUrl: String = "",
    val iconRes: Int = 0,
)
