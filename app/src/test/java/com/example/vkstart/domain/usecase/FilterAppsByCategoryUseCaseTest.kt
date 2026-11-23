package com.example.vkstart.domain.usecase

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class FilterAppsByCategoryUseCaseTest {

    private lateinit var fakeRepository: FakeAppsRepository
    private lateinit var useCase: FilterAppsByCategoryUseCase

    @Before
    fun setUp() {
        fakeRepository = FakeAppsRepository()
        useCase = FilterAppsByCategoryUseCase(fakeRepository)
    }

    @Test
    fun invoke_withSpecificCategory_returnsFilteredApps() = runTest {
        val result = useCase("Social")
        assertEquals(2, result.size)
        assertEquals(listOf("1", "3"), result.map { it.id })
    }

    @Test
    fun invoke_withNullOrBlankCategory_returnsAllApps() = runTest {
        val resultNull = useCase(null)
        assertEquals(5, resultNull.size)

        val resultBlank = useCase("   ")
        assertEquals(5, resultBlank.size)
    }

    @Test
    fun invoke_withAllCategory_returnsAllApps() = runTest {
        val resultAll = useCase("Все")
        assertEquals(5, resultAll.size)
    }

    @Test
    fun invoke_withNonExistentCategory_returnsEmptyList() = runTest {
        val result = useCase("NonExistentCategory")
        assertEquals(0, result.size)
    }
}
