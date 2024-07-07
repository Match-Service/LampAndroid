package com.devndev.data.di

import com.devndev.data.repository.ItemRepositoryImpl
import com.devndev.domain.repository.ItemRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {
    @Singleton
    @Binds
    fun bindItemRepository(itemRepositoryImpl: ItemRepositoryImpl): ItemRepository
}
