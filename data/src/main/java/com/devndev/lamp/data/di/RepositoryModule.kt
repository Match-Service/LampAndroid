package com.devndev.lamp.data.di

import com.devndev.lamp.data.repository.GoogleTokenRepositoryImpl
import com.devndev.lamp.data.repository.ItemRepositoryImpl
import com.devndev.lamp.data.repository.SignUpRepositoryImpl
import com.devndev.lamp.data.repository.UserRepositoryImpl
import com.devndev.lamp.domain.repository.GoogleTokenRepository
import com.devndev.lamp.domain.repository.ItemRepository
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
    fun bindItemRepository(itemRepositoryImpl: ItemRepositoryImpl): ItemRepository

    @Singleton
    @Binds
    fun bindUserRepository(userRepositoryImpl: UserRepositoryImpl): UserRepository

    @Singleton
    @Binds
    fun bindGoogleTokenRepository(googleTokenRepositoryImpl: GoogleTokenRepositoryImpl): GoogleTokenRepository

    @Singleton
    @Binds
    fun bindSignUpRepository(signUpRepositoryImpl: SignUpRepositoryImpl): SignUpRepository
}
