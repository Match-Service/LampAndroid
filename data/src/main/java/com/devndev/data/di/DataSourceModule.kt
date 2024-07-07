package com.devndev.data.di

import com.devndev.data.datsource.ItemDataSource
import com.devndev.data.datsource.ItemDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface DataSourceModule {
    @Singleton
    @Binds
    fun bindLocalDataSource(itemDataSourceImpl: ItemDataSourceImpl): ItemDataSource
}
