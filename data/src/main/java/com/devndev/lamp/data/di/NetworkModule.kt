package com.devndev.lamp.data.di

import com.devndev.lamp.data.api.ApiService
import com.devndev.lamp.domain.model.Item
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    @Singleton
    fun provideApiService(): ApiService {
        return object : ApiService {
            override suspend fun getItems(): List<Item> {
                // 더미 데이터 반환
                return listOf(Item(1, "Dummy item 1"), Item(2, "Dummy item 2"))
            }
        }
    }
}
