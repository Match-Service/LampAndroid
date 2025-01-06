package com.devndev.lamp.data.di

import com.devndev.lamp.data.datsource.GoogleTokenDataSource
import com.devndev.lamp.data.datsource.GoogleTokenDataSourceImpl
import com.devndev.lamp.data.datsource.ItemDataSource
import com.devndev.lamp.data.datsource.ItemDataSourceImpl
import com.devndev.lamp.data.datsource.LocalDataSource
import com.devndev.lamp.data.datsource.LocalDataSourceImpl
import com.devndev.lamp.data.datsource.NotificationDataSource
import com.devndev.lamp.data.datsource.NotificationDataSourceImpl
import com.devndev.lamp.data.datsource.SignUpDataSource
import com.devndev.lamp.data.datsource.SignUpDataSourceImpl
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
    fun bindItemDataSource(itemDataSourceImpl: ItemDataSourceImpl): ItemDataSource

    @Singleton
    @Binds
    fun bindUserDataSource(userDataSourceImpl: UserDataSourceImpl): UserDataSource

    @Singleton
    @Binds
    fun bindGoogleTokenDataSource(googleTokenDataSourceImpl: GoogleTokenDataSourceImpl): GoogleTokenDataSource

    @Singleton
    @Binds
    fun bindSignUpDataSource(signUpDataSourceImpl: SignUpDataSourceImpl): SignUpDataSource

    @Singleton
    @Binds
    fun bindLocalDataSource(dataSourceImpl: LocalDataSourceImpl): LocalDataSource

    @Singleton
    @Binds
    fun bindNotificationDataSource(notificationDataSourceImpl: NotificationDataSourceImpl): NotificationDataSource
}
