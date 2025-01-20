package com.devndev.lamp.data.datsource.login

import com.devndev.lamp.data.dto.request.login.GoogleTokenRequest
import com.devndev.lamp.data.dto.response.login.GoogleTokenResponse

interface GoogleTokenDataSource {
    suspend fun getGoogleAuth(googleTokenRequest: GoogleTokenRequest): GoogleTokenResponse
}
