package com.devndev.lamp.data.di

import android.content.Context
import com.devndev.lamp.data.BuildConfig
import com.devndev.lamp.data.di.qualifier.DefaultRetrofit
import com.devndev.lamp.data.service.ApiService
import com.devndev.lamp.data.service.UserService
import com.devndev.lamp.domain.model.Item
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal class NetworkModule {
    @Provides
    @Singleton
    fun provideApiService(): ApiService {
        return object : ApiService {
            override suspend fun getItems(): List<Item> {
                // 더미 데이터 반환
                return listOf(Item("김수환", true, "멋쟁이 친구들"), Item("김수환무거북", false, null))
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

    @DefaultRetrofit
    @Provides
    @Singleton
    fun provideDefaultRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun provideUserService(
        @DefaultRetrofit retrofit: Retrofit
    ): UserService = retrofit.create()

    companion object {
        private const val BASE_URL = "http://13.125.174.56:3000/"
    }
}
