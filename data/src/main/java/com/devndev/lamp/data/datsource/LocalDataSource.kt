package com.devndev.lamp.data.datsource

interface LocalDataSource {
    fun getIsNeedSignOut(): Boolean
    fun saveIsNeedSignOut(isNeedSignOut: Boolean)
}
