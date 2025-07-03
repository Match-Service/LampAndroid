package com.devndev.lamp.data.datsource.local

interface LocalDataSource {
    fun getIsNeedSignOut(): Boolean
    fun saveIsNeedSignOut(isNeedSignOut: Boolean)
    fun getToken(): String
    fun setToken(token: String)
    fun putBoolean(key: String, value: Boolean)
    fun getBoolean(key: String, value: Boolean): Boolean
    fun removeToken()
    fun saveIsFirstOpen(isFirstOpen: Boolean)
    fun getIsFirstOpen(): Boolean
}
