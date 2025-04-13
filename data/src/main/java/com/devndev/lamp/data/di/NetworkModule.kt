package com.devndev.lamp.data.di

import android.content.Context
import com.devndev.lamp.data.BuildConfig
import com.devndev.lamp.data.di.qualifier.DefaultClient
import com.devndev.lamp.data.di.qualifier.DefaultRetrofit
import com.devndev.lamp.data.interceptor.AuthInterceptor
import com.devndev.lamp.data.service.AlarmService
import com.devndev.lamp.data.service.LampService
import com.devndev.lamp.data.service.LoginService
import com.devndev.lamp.data.service.SignUpService
import com.devndev.lamp.data.service.UserService
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal class NetworkModule {
    @Provides
    @Singleton
    fun provideGoogleSignInClient(@ApplicationContext context: Context): GoogleSignInClient {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(BuildConfig.GOOGLE_CLIENT_ID)
            .requestEmail()
            .build()
        return GoogleSignIn.getClient(context, gso)
    }

    @DefaultClient
    @Singleton
    @Provides
    fun provideDefaultOkHttpClient(
        authInterceptor: AuthInterceptor
    ): OkHttpClient {
        return OkHttpClient
            .Builder()
            .addInterceptor(authInterceptor)
            .build()
    }

    @DefaultRetrofit
    @Provides
    @Singleton
    fun provideDefaultRetrofit(
        @DefaultClient okHttpClient: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun provideUserService(
        @DefaultRetrofit retrofit: Retrofit
    ): UserService = retrofit.create()

    @Singleton
    @Provides
    fun provideLoginService(
        @DefaultRetrofit retrofit: Retrofit
    ): LoginService = retrofit.create()

    @Singleton
    @Provides
    fun provideSignUpService(
        @DefaultRetrofit retrofit: Retrofit
    ): SignUpService = retrofit.create()

    @Singleton
    @Provides
    fun provideLampService(
        @DefaultRetrofit retrofit: Retrofit
    ): LampService = retrofit.create()

    @Singleton
    @Provides
    fun provideAlarmService(
        @DefaultRetrofit retrofit: Retrofit
    ): AlarmService = retrofit.create()

    companion object {
        private const val BASE_URL = "https://dev-api.lamp-app.shop/"
//        private const val BASE_URL = "http://192.168.0.25:3000/"
    }
}
