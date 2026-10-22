package com.example.vkstart.data

import com.example.vkstart.R
import com.example.vkstart.model.AppInfo
import com.example.vkstart.model.AppDetails

/**
 * Захардкоженные тестовые данные списка приложений.
 */
object SampleData {
    const val SHOWCASE_PAGE_COUNT = 3
    const val APPS_PER_PAGE = 3
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
        ),
        AppInfo(
            id = 8,
            name = "Облако Mail",
            description = "Хранение фотографий, документов и резервных копий",
            category = "Инструменты",
            iconRes = R.drawable.ic_cloud
        ),
        AppInfo(
            id = 9,
            name = "Гильдия Героев: Экшен ММО РПГ",
            description = "Легендарный рейд героев в Фэнтези РПГ. Станьте героем гильдии и сразите мастера подземелья!",
            category = "Игры",
            iconRes = R.drawable.ic_vk_play
        )
    )

    // Exactly nine apps, three pages; never repeat items to fill a page.
    val showcasePages: List<List<AppInfo>> = sampleApps.chunked(APPS_PER_PAGE).also { pages ->
        require(pages.size == SHOWCASE_PAGE_COUNT && pages.all { it.size == APPS_PER_PAGE })
    }

    private val guildScreenshots = listOf(
        "https://static.rustore.ru/apk/393868735/content/SCREENSHOT/dfd33017-e90d-4990-aa8c-6f159d546788.jpg",
        "https://static.rustore.ru/apk/393868735/content/SCREENSHOT/60ec4cbc-dcf6-4e69-aa6f-cc2da7de1af6.jpg",
        "https://static.rustore.ru/apk/393868735/content/SCREENSHOT/c2dde8bc-c4ab-482a-80a5-2789149f598d.jpg",
    )

    fun detailsFor(id: Int): AppDetails {
        val app = sampleApps.first { it.id == id }
        return AppDetails(
            id = app.id,
            name = app.name,
            developer = if (id == 9) "VK Play" else "VK",
            category = app.category,
            ageRating = if (id == 6) 6 else 12,
            size = if (id == 9) 223.7f else 85f,
            iconRes = app.iconRes,
            screenshotUrlList = if (id == 9) guildScreenshots else emptyList(),
            description = app.description,
        )
    }
}
