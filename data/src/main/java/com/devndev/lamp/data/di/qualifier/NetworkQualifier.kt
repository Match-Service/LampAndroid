package com.devndev.lamp.data.di.qualifier

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class DefaultRetrofit

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class AuthRetrofit
