package com.example.vkstart.domain.usecase

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class GetAppsUseCaseTest {

    private lateinit var fakeRepository: FakeAppsRepository
    private lateinit var useCase: GetAppsUseCase

    @Before
    fun setUp() {
        fakeRepository = FakeAppsRepository()
        useCase = GetAppsUseCase(fakeRepository)
    }

    @Test
    fun invoke_returnsAppsListFromRepository() = runTest {
        val result = useCase()
        assertEquals(fakeRepository.appsList, result)
    }

    @Test(expected = RuntimeException::class)
    fun invoke_whenRepositoryThrows_propagatesException() = runTest {
        fakeRepository.shouldThrowException = true
        useCase()
    }
}
