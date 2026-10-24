package com.example.vkstart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import com.example.vkstart.data.AppsRepository
import com.example.vkstart.data.SampleData
import com.example.vkstart.ui.screens.AppListViewModel
import org.junit.Assert.assertEquals
import org.junit.Assert.assertSame
import org.junit.Test

class AppListViewModelTest {
    @Test
    fun retainedViewModelKeepsContentWithoutLoadingAgain() {
        var loads = 0
        val apps = SampleData.sampleApps
        val repository = AppsRepository {
            loads++
            apps
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
        assertEquals(apps, original.uiState.value.apps)

        val recreated = ViewModelProvider(retainedStore, factory)[AppListViewModel::class.java]
        assertSame(original, recreated)
        assertSame(original.uiState.value, recreated.uiState.value)
        assertEquals(1, loads)
    }
}
