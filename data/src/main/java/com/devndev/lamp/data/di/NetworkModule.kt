package com.devndev.lamp.data.di

import android.content.Context
import com.devndev.lamp.data.BuildConfig
import com.devndev.lamp.data.api.ApiService
import com.devndev.lamp.domain.model.Item
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
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
                return listOf(Item("김수환", true, "멋쟁이 친구들"), Item("김수헌", false, null))
            }
        }
    }

    @Provides
    @Singleton
    fun provideGoogleSignInClient(@ApplicationContext context: Context): GoogleSignInClient {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(BuildConfig.GOOGLE_CLIENT_ID)
            .requestEmail()
            .build()
        return GoogleSignIn.getClient(context, gso)
    }
}
