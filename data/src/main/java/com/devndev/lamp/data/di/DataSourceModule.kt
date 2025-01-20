package com.devndev.lamp.data.di

import com.devndev.lamp.data.datsource.lamp.LampDataSource
import com.devndev.lamp.data.datsource.lamp.LampDataSourceImpl
import com.devndev.lamp.data.datsource.local.LocalDataSource
import com.devndev.lamp.data.datsource.local.LocalDataSourceImpl
import com.devndev.lamp.data.datsource.login.GoogleTokenDataSource
import com.devndev.lamp.data.datsource.login.GoogleTokenDataSourceImpl
import com.devndev.lamp.data.datsource.notification.NotificationDataSource
import com.devndev.lamp.data.datsource.notification.NotificationDataSourceImpl
import com.devndev.lamp.data.datsource.signup.SignUpDataSource
import com.devndev.lamp.data.datsource.signup.SignUpDataSourceImpl
import com.devndev.lamp.data.datsource.user.UserDataSource
import com.devndev.lamp.data.datsource.user.UserDataSourceImpl
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

    @Singleton
    @Binds
    fun bindLampDataSource(lampDataSourceImpl: LampDataSourceImpl): LampDataSource
}
