package com.devndev.lamp.data.datasource.local

interface LocalDataSource {
    fun getIsNeedSignOut(): Boolean
    fun saveIsNeedSignOut(isNeedSignOut: Boolean)
    fun getToken(): String
    fun setToken(token: String)
    fun removeToken()
}
