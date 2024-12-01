package com.devndev.lamp.data.datsource

interface LocalDataSource {
    fun getIsNeedSignOut(): Boolean
    fun saveIsNeedSignOut(isNeedSignOut: Boolean)
    fun getToken(): String
    fun setToken(token: String)
}
