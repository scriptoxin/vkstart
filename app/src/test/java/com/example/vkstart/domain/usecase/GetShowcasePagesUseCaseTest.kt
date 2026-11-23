package com.example.vkstart.domain.usecase

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class GetShowcasePagesUseCaseTest {

    private lateinit var fakeRepository: FakeAppsRepository
    private lateinit var useCase: GetShowcasePagesUseCase

    @Before
    fun setUp() {
        fakeRepository = FakeAppsRepository()
        useCase = GetShowcasePagesUseCase(fakeRepository)
    }

    @Test
    fun invoke_chunksAppsListIntoPagesOfDefaultSize() = runTest {
        // 5 apps chunked by 3 -> 2 pages (3 and 2)
        val pages = useCase()
        assertEquals(2, pages.size)
        assertEquals(3, pages[0].size)
        assertEquals(2, pages[1].size)
    }

    @Test
    fun invoke_withCustomPageSize_chunksCorrectly() = runTest {
        // 5 apps chunked by 2 -> 3 pages (2, 2, and 1)
        val pages = useCase(pageSize = 2)
        assertEquals(3, pages.size)
        assertEquals(2, pages[0].size)
        assertEquals(2, pages[1].size)
        assertEquals(1, pages[2].size)
    }

    @Test(expected = IllegalArgumentException::class)
    fun invoke_withZeroPageSize_throwsIllegalArgumentException() = runTest {
        useCase(pageSize = 0)
    }
}
