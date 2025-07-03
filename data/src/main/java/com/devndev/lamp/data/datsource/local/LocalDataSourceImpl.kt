package com.devndev.lamp.data.datsource.local

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
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
    override fun putBoolean(key: String, value: Boolean) {
        sharedPreferences.edit().putBoolean(key, value).apply()
    }

    @Synchronized
    override fun getBoolean(key: String, value: Boolean): Boolean {
        return sharedPreferences.getBoolean(key, value)
    }

    @Synchronized
    fun deleteData(key: String) {
        sharedPreferences.edit().remove(key).apply()
    }

    private fun remove(key: String) {
        return sharedPreferences.edit {
            remove(key)
        }
    }

    override fun getIsNeedSignOut(): Boolean {
        return getBoolean(KEY_IS_NEED_SIGN_OUT, false)
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

    override fun removeToken() {
        remove(KEY_TOKEN)
    }

    override fun saveIsFirstOpen(isFirstOpen: Boolean) {
        putBoolean(KEY_IS_FIRST_OPEN, isFirstOpen)
    }

    override fun getIsFirstOpen(): Boolean {
        return getBoolean(KEY_IS_FIRST_OPEN, true)
    }

    companion object {
        private const val PREF_NAME = "lamp_preferences"
        private const val KEY_IS_NEED_SIGN_OUT = "key_access_token"
        private const val KEY_TOKEN = "key_token"
        private const val KEY_IS_FIRST_OPEN = "key_is_first_open"
    }
}
