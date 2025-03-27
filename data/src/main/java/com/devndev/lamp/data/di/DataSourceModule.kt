package com.devndev.lamp.data.di

import com.devndev.lamp.data.datsource.alarm.AlarmDataSource
import com.devndev.lamp.data.datsource.alarm.AlarmDataSourceImpl
import com.devndev.lamp.data.datsource.lamp.LampDataSource
import com.devndev.lamp.data.datsource.lamp.LampDataSourceImpl
import com.devndev.lamp.data.datsource.lampmatach.LampMatchDataSource
import com.devndev.lamp.data.datsource.lampmatach.LampMatchDataSourceImpl
import com.devndev.lamp.data.datsource.local.LocalDataSource
import com.devndev.lamp.data.datsource.local.LocalDataSourceImpl
import com.devndev.lamp.data.datsource.login.GoogleTokenDataSource
import com.devndev.lamp.data.datsource.login.GoogleTokenDataSourceImpl
import com.devndev.lamp.data.datsource.signup.SignUpDataSource
import com.devndev.lamp.data.datsource.signup.SignUpDataSourceImpl
import com.devndev.lamp.data.datsource.socket.LampSocketDataSource
import com.devndev.lamp.data.datsource.socket.LampSocketDataSourceImpl
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
}
