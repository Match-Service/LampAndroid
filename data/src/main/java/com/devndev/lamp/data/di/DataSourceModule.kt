package com.devndev.lamp.data.di

import com.devndev.lamp.data.datasource.alarm.AlarmDataSource
import com.devndev.lamp.data.datasource.alarm.AlarmDataSourceImpl
import com.devndev.lamp.data.datasource.chat.ChatDataSource
import com.devndev.lamp.data.datasource.chat.ChatDataSourceImpl
import com.devndev.lamp.data.datasource.lamp.LampDataSource
import com.devndev.lamp.data.datasource.lamp.LampDataSourceImpl
import com.devndev.lamp.data.datasource.lampmatch.LampMatchDataSource
import com.devndev.lamp.data.datasource.lampmatch.LampMatchDataSourceImpl
import com.devndev.lamp.data.datasource.local.LocalDataSource
import com.devndev.lamp.data.datasource.local.LocalDataSourceImpl
import com.devndev.lamp.data.datasource.login.GoogleTokenDataSource
import com.devndev.lamp.data.datasource.login.GoogleTokenDataSourceImpl
import com.devndev.lamp.data.datasource.signup.SignUpDataSource
import com.devndev.lamp.data.datasource.signup.SignUpDataSourceImpl
import com.devndev.lamp.data.datasource.socket.LampSocketDataSource
import com.devndev.lamp.data.datasource.socket.LampSocketDataSourceImpl
import com.devndev.lamp.data.datasource.user.UserDataSource
import com.devndev.lamp.data.datasource.user.UserDataSourceImpl
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
    fun bindLampDataSource(lampDataSourceImpl: LampDataSourceImpl): LampDataSource

    @Singleton
    @Binds
    fun bindAlarmDataSource(alarmDataSourceImpl: AlarmDataSourceImpl): AlarmDataSource

    @Singleton
    @Binds
    fun bindLampMatchDataSource(lampMatchDataSourceImpl: LampMatchDataSourceImpl): LampMatchDataSource

    @Singleton
    @Binds
    fun bindLampSocketDataSource(lampSocketDataSourceImpl: LampSocketDataSourceImpl): LampSocketDataSource

    @Singleton
    @Binds
    fun bindChatDataSource(chatDataSourceImpl: ChatDataSourceImpl): ChatDataSource
}
