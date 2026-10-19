package com.example.vkstart.data

import com.example.vkstart.R
import com.example.vkstart.model.AppInfo

/**
 * Захардкоженные тестовые данные списка приложений.
 */
object SampleData {
    val sampleApps = listOf(
        AppInfo(
            id = 1,
            name = "ВКонтакте",
            description = "Общение, музыка, видео, новости и сервисы в одном приложении",
            category = "Социальные сети",
            iconRes = R.drawable.ic_vk
        ),
        AppInfo(
            id = 2,
            name = "VK Музыка",
            description = "Миллионы треков, подкасты, радио и персональные плейлисты",
            category = "Музыка",
            iconRes = R.drawable.ic_vk_music
        ),
        AppInfo(
            id = 3,
            name = "VK Видео",
            description = "Фильмы, сериалы, шоу, стримы и эксклюзивный контент блогеров",
            category = "Видео",
            iconRes = R.drawable.ic_vk_video
        ),
        AppInfo(
            id = 4,
            name = "VK Мессенджер",
            description = "Быстрые чаты, групповые видеозвонки и обмен файлами",
            category = "Общение",
            iconRes = R.drawable.ic_vk_messenger
        ),
        AppInfo(
            id = 5,
            name = "VK Play",
            description = "Игровой каталог, новости, облачный гейминг и киберспорт",
            category = "Игры",
            iconRes = R.drawable.ic_vk_play
        ),
        AppInfo(
            id = 6,
            name = "Сферум",
            description = "Безопасное учебное пространство, уроки и школьные коммуникации",
            category = "Образование",
            iconRes = R.drawable.ic_sferum
        ),
        AppInfo(
            id = 7,
            name = "Почта Mail.ru",
            description = "Удобный почтовый клиент с защитой от спама и умными папками",
            category = "Инструменты",
            iconRes = R.drawable.ic_mail
        )
    )
}
