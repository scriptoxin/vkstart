package com.example.vkstart.domain.usecase

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class GetAppDetailsUseCaseTest {

    private lateinit var fakeRepository: FakeAppsRepository
    private lateinit var useCase: GetAppDetailsUseCase

    @Before
    fun setUp() {
        fakeRepository = FakeAppsRepository()
        useCase = GetAppDetailsUseCase(fakeRepository)
    }

    @Test
    fun invoke_returnsAppDetailsForGivenId() = runTest {
        val details = useCase("1")
        assertEquals("1", details.id)
        assertEquals("App 1", details.name)
        assertEquals("Dev 1", details.developer)
    }

    @Test(expected = NoSuchElementException::class)
    fun invoke_withUnknownId_propagatesException() = runTest {
        useCase("unknown_id")
    }
}
