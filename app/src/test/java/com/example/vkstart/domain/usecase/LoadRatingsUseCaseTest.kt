package com.example.vkstart.domain.usecase

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class LoadRatingsUseCaseTest {

    private lateinit var fakeRepository: FakeAppsRepository
    private lateinit var useCase: LoadRatingsUseCase

    @Before
    fun setUp() {
        fakeRepository = FakeAppsRepository()
        useCase = LoadRatingsUseCase(fakeRepository)
    }

    @Test
    fun invoke_withParallelFalse_callsLoadRatingsSequentially() = runTest {
        val ids = listOf("1", "2", "3")
        val result = useCase(ids, parallel = false)

        assertNotNull(fakeRepository.sequentialCalledWith)
        assertEquals(ids, fakeRepository.sequentialCalledWith)
        assertNull(fakeRepository.parallelCalledWith)
        assertEquals(3, result.size)
    }

    @Test
    fun invoke_withParallelTrue_callsLoadRatingsInParallel() = runTest {
        val ids = listOf("1", "2")
        val result = useCase(ids, parallel = true)

        assertNotNull(fakeRepository.parallelCalledWith)
        assertEquals(ids, fakeRepository.parallelCalledWith)
        assertNull(fakeRepository.sequentialCalledWith)
        assertEquals(2, result.size)
    }
}
