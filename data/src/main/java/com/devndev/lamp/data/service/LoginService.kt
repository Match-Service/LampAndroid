package com.devndev.lamp.data.service

import com.devndev.lamp.data.dto.request.login.GoogleTokenRequest
import com.devndev.lamp.data.dto.response.login.GoogleTokenResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginService {
    @POST("/api/v1/oauth2/token")
    suspend fun getGoogleAuth(
        @Body googleTokenRequest: GoogleTokenRequest
    ): GoogleTokenResponse
}
