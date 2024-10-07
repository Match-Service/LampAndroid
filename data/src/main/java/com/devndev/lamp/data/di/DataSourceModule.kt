package com.devndev.lamp.data.di

import com.devndev.lamp.data.datsource.ItemDataSource
import com.devndev.lamp.data.datsource.ItemDataSourceImpl
import com.devndev.lamp.data.datsource.UserDataSource
import com.devndev.lamp.data.datsource.UserDataSourceImpl
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

    @Singleton
    @Binds
    fun bindUserDataSource(userDataSourceImpl: UserDataSourceImpl): UserDataSource
}
