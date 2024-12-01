package com.devndev.lamp.data.datsource

import android.content.Context
import android.content.SharedPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class LocalDataSourceImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : LocalDataSource {

    private val sharedPreferences: SharedPreferences by lazy {
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }

    @Synchronized
    fun putString(key: String, value: String) {
        sharedPreferences.edit().putString(key, value).apply()
    }

    @Synchronized
    fun getString(key: String): String? {
        return sharedPreferences.getString(key, "")
    }

    @Synchronized
    fun putBoolean(key: String, value: Boolean) {
        sharedPreferences.edit().putBoolean(key, value).apply()
    }

    @Synchronized
    fun getBoolean(key: String): Boolean {
        return sharedPreferences.getBoolean(key, false)
    }

    @Synchronized
    fun deleteData(key: String) {
        sharedPreferences.edit().remove(key).apply()
    }

    override fun getIsNeedSignOut(): Boolean {
        return getBoolean(KEY_IS_NEED_SIGN_OUT)
    }

    override fun saveIsNeedSignOut(isNeedSignOut: Boolean) {
        putBoolean(KEY_IS_NEED_SIGN_OUT, isNeedSignOut)
    }

    override fun getToken(): String {
        return getString(KEY_TOKEN) ?: ""
    }

    override fun setToken(token: String) {
        putString(KEY_TOKEN, token)
    }

    companion object {
        private const val PREF_NAME = "lamp_preferences"
        private const val KEY_IS_NEED_SIGN_OUT = "key_access_token"
        private const val KEY_TOKEN = "key_token"
    }
}
