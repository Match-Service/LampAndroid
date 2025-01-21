package com.devndev.lamp.domain.repository

import com.devndev.lamp.domain.model.login.GoogleTokenDomainModel
import com.devndev.lamp.domain.model.login.GoogleTokenParam

interface LoginRepository {
    suspend fun getGoogleAuth(googleTokenParam: GoogleTokenParam): GoogleTokenDomainModel
    fun getIsNeedSignOut(): Boolean
    fun saveIsNeedSignOut(isNeedSignOut: Boolean)
    fun setToken(token: String)
    fun removeToken()
    fun isUserLoggedIn(): Boolean
}
