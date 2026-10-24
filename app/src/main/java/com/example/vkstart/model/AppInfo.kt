package com.example.vkstart.model

import androidx.annotation.DrawableRes

/**
 * Модель данных для приложения в списке.
 *
 * @property id Уникальный идентификатор приложения
 * @property name Название приложения
 * @property description Краткое описание приложения
 * @property category Категория приложения
 * @property iconRes Ресурс иконки приложения
 */
data class AppInfo(
    val id: Int,
    val name: String,
    val description: String,
    val category: String,
    @param:DrawableRes val iconRes: Int
)
