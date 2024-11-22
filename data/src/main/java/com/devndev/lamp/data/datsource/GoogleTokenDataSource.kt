package com.devndev.lamp.data.datsource

import com.devndev.lamp.data.dto.request.GoogleTokenRequest
import com.devndev.lamp.data.dto.response.GoogleTokenResponse

interface GoogleTokenDataSource {
    suspend fun getGoogleAuth(googleTokenRequest: GoogleTokenRequest): GoogleTokenResponse
}
