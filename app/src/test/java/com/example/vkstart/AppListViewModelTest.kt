package com.example.vkstart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import com.example.vkstart.domain.repository.AppsRepository
import com.example.vkstart.data.SampleAppsRepository
import com.example.vkstart.presentation.screens.AppListViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertSame
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class AppListViewModelTest {
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun retainedViewModelKeepsContentWithoutLoadingAgain() = runBlocking {
        var loads = 0
        val apps = SampleAppsRepository.getApps()
        val repository = object : AppsRepository {
            override suspend fun getApps(): List<com.example.vkstart.domain.model.AppInfo> {
                loads++
                return apps
            }
            override suspend fun getAppDetails(id: String) = SampleAppsRepository.getAppDetails(id)
        }
        val factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return AppListViewModel(repository) as T
            }
        }
        // A new owner after configuration change receives the retained store.
        val retainedStore = ViewModelStore()
        val original = ViewModelProvider(retainedStore, factory)[AppListViewModel::class.java]
        testDispatcher.scheduler.advanceUntilIdle()
        assertEquals(apps, original.uiState.value.apps)

        val recreated = ViewModelProvider(retainedStore, factory)[AppListViewModel::class.java]
        assertSame(original, recreated)
        assertSame(original.uiState.value, recreated.uiState.value)
        assertEquals(1, loads)
    }
}
