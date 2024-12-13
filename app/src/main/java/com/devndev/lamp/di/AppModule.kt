package com.devndev.lamp.di

import android.content.Context
import com.devndev.lamp.domain.manager.AppIconManager
import com.devndev.lamp.manager.AppIconManagerImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    fun provideAppIconManager(@ApplicationContext context: Context): AppIconManager {
        return AppIconManagerImpl(context)
    }
}
