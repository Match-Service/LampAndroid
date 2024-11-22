package com.devndev.lamp.domain.repository

import com.devndev.lamp.domain.model.GoogleTokenDomainModel
import com.devndev.lamp.domain.model.GoogleTokenParam

interface GoogleTokenRepository {
    suspend fun getGoogleAuth(googleTokenParam: GoogleTokenParam): GoogleTokenDomainModel
}
