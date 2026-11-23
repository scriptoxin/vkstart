package com.example.vkstart.di

import com.example.vkstart.data.AppsRepositoryImpl
import com.example.vkstart.domain.repository.AppsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindAppsRepository(
        impl: AppsRepositoryImpl,
    ): AppsRepository
}
