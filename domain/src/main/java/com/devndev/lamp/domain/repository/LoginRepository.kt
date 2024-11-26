package com.devndev.lamp.domain.repository

import com.devndev.lamp.domain.model.GoogleTokenDomainModel
import com.devndev.lamp.domain.model.GoogleTokenParam

interface LoginRepository {
    suspend fun getGoogleAuth(googleTokenParam: GoogleTokenParam): GoogleTokenDomainModel
    fun getIsNeedSignOut(): Boolean
    fun saveIsNeedSignOut(isNeedSignOut: Boolean)
}
