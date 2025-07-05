package com.devndev.lamp.data.di

import com.devndev.lamp.data.repository.AlarmRepositoryImpl
import com.devndev.lamp.data.repository.AssessmentRepositoryImpl
import com.devndev.lamp.data.repository.ChatRepositoryImpl
import com.devndev.lamp.data.repository.ConfigRepositoryImpl
import com.devndev.lamp.data.repository.LampMatchRepositoryImpl
import com.devndev.lamp.data.repository.LampRepositoryImpl
import com.devndev.lamp.data.repository.LampSocketRepositoryImpl
import com.devndev.lamp.data.repository.LocalRepositoryImpl
import com.devndev.lamp.data.repository.LoginRepositoryImpl
import com.devndev.lamp.data.repository.SignUpRepositoryImpl
import com.devndev.lamp.data.repository.UserRepositoryImpl
import com.devndev.lamp.domain.repository.AlarmRepository
import com.devndev.lamp.domain.repository.AssessmentRepository
import com.devndev.lamp.domain.repository.ChatRepository
import com.devndev.lamp.domain.repository.ConfigRepository
import com.devndev.lamp.domain.repository.LampMatchRepository
import com.devndev.lamp.domain.repository.LampRepository
import com.devndev.lamp.domain.repository.LampSocketRepository
import com.devndev.lamp.domain.repository.LocalRepository
import com.devndev.lamp.domain.repository.LoginRepository
import com.devndev.lamp.domain.repository.SignUpRepository
import com.devndev.lamp.domain.repository.UserRepository
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
    fun bindUserRepository(userRepositoryImpl: UserRepositoryImpl): UserRepository

    @Singleton
    @Binds
    fun bindLoginRepository(googleTokenRepositoryImpl: LoginRepositoryImpl): LoginRepository

    @Singleton
    @Binds
    fun bindSignUpRepository(signUpRepositoryImpl: SignUpRepositoryImpl): SignUpRepository

    @Singleton
    @Binds
    fun bindLampRepository(lampRepositoryImpl: LampRepositoryImpl): LampRepository

    @Singleton
    @Binds
    fun bindAlarmRepository(alarmRepositoryImpl: AlarmRepositoryImpl): AlarmRepository

    @Singleton
    @Binds
    fun bindLampMatchRepository(lampMatchRepositoryImpl: LampMatchRepositoryImpl): LampMatchRepository

    @Singleton
    @Binds
    fun bindLampSocketRepository(lampSocketRepositoryImpl: LampSocketRepositoryImpl): LampSocketRepository

    @Singleton
    @Binds
    fun bindChatRepository(chatRepositoryImpl: ChatRepositoryImpl): ChatRepository

    @Singleton
    @Binds
    fun bindConfigRepository(configRepositoryImpl: ConfigRepositoryImpl): ConfigRepository

    @Singleton
    @Binds
    fun bindLocalRepository(localRepositoryImpl: LocalRepositoryImpl): LocalRepository

    @Singleton
    @Binds
    fun bindAssessmentRepository(assessmentRepositoryImpl: AssessmentRepositoryImpl): AssessmentRepository
}
