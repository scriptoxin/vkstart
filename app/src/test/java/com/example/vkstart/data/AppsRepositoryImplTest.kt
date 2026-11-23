package com.example.vkstart.data

import com.example.vkstart.data.api.CatalogApiService
import com.example.vkstart.data.dto.AppDetailsDto
import com.example.vkstart.data.dto.AppInfoDto
import com.example.vkstart.data.mapper.AppMapper
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class AppsRepositoryImplTest {

    private class FakeCatalogApiService : CatalogApiService {
        var catalogResult: Result<List<AppInfoDto>> = Result.success(emptyList())
        var detailsResult: Result<AppDetailsDto> = Result.success(
            AppDetailsDto(
                id = "1",
                name = "Test App",
                developer = "Test Dev",
                category = "Test Cat",
                ageRating = 0,
                size = 10f,
                iconUrl = "url",
                iconRes = 0,
                screenshotUrlList = emptyList(),
                description = "Desc",
            )
        )
        var ratingResult: Float = 4.5f

        override suspend fun getCatalog(): List<AppInfoDto> {
            return catalogResult.getOrThrow()
        }

        override suspend fun getAppDetails(id: String): AppDetailsDto {
            return detailsResult.getOrThrow()
        }

        override suspend fun getRating(id: String): Float {
            return ratingResult
        }
    }

    private lateinit var fakeApi: FakeCatalogApiService
    private lateinit var mapper: AppMapper
    private lateinit var repository: AppsRepositoryImpl

    @Before
    fun setUp() {
        fakeApi = FakeCatalogApiService()
        mapper = AppMapper()
        repository = AppsRepositoryImpl(fakeApi, mapper)
    }

    @Test
    fun getApps_success_returnsMappedDomainApps() = runTest {
        val dtos = listOf(
            AppInfoDto("1", "App 1", "Desc 1", "Tools", "http://icon1.png", 101),
            AppInfoDto("2", "App 2", "Desc 2", "Games", "http://icon2.png", 102),
        )
        fakeApi.catalogResult = Result.success(dtos)

        val result = repository.getApps()

        assertEquals(2, result.size)
        assertEquals("1", result[0].id)
        assertEquals("App 1", result[0].name)
        assertEquals("http://icon1.png", result[0].iconUrl)
        assertEquals("2", result[1].id)
        assertEquals("App 2", result[1].name)
    }

    @Test
    fun getApps_failure_returnsFallbackSampleApps() = runTest {
        fakeApi.catalogResult = Result.failure(RuntimeException("Network error"))

        val result = repository.getApps()

        val expected = SampleData.sampleApps.map(mapper::toDomain)
        assertEquals(expected, result)
    }

    @Test
    fun getAppDetails_success_returnsMappedDomainDetails() = runTest {
        val dto = AppDetailsDto(
            id = "app_123",
            name = "VK Music",
            developer = "VK",
            category = "Music",
            ageRating = 12,
            size = 50.0f,
            iconUrl = "http://icon.png",
            iconRes = 1,
            screenshotUrlList = listOf("shot1.png", "shot2.png"),
            description = "Detailed music app description",
        )
        fakeApi.detailsResult = Result.success(dto)

        val result = repository.getAppDetails("app_123")

        assertEquals("app_123", result.id)
        assertEquals("VK Music", result.name)
        assertEquals("VK", result.developer)
        assertEquals(listOf("shot1.png", "shot2.png"), result.screenshotUrlList)
    }

    @Test
    fun getAppDetails_failure_returnsFallbackSampleDetails() = runTest {
        fakeApi.detailsResult = Result.failure(RuntimeException("Server 500"))

        val result = repository.getAppDetails("app_vk_music")

        val expected = mapper.toDomain(SampleData.detailsFor("app_vk_music"))
        assertEquals(expected, result)
    }

    @Test
    fun getRating_returnsRatingFromApiService() = runTest {
        fakeApi.ratingResult = 4.7f

        val rating = repository.getRating("app_1")

        assertEquals(4.7f, rating, 0.001f)
    }

    @Test
    fun loadRatingsSequentially_returnsMapForListOfIds() = runTest {
        fakeApi.ratingResult = 4.2f
        val ids = listOf("1", "2", "3")

        val ratingsMap = repository.loadRatingsSequentially(ids)

        assertEquals(3, ratingsMap.size)
        assertEquals(4.2f, ratingsMap["1"]!!, 0.001f)
        assertEquals(4.2f, ratingsMap["2"]!!, 0.001f)
        assertEquals(4.2f, ratingsMap["3"]!!, 0.001f)
    }

    @Test
    fun loadRatingsInParallel_returnsMapForListOfIds() = runTest {
        fakeApi.ratingResult = 4.9f
        val ids = listOf("10", "20")

        val ratingsMap = repository.loadRatingsInParallel(ids)

        assertEquals(2, ratingsMap.size)
        assertEquals(4.9f, ratingsMap["10"]!!, 0.001f)
        assertEquals(4.9f, ratingsMap["20"]!!, 0.001f)
    }
}
