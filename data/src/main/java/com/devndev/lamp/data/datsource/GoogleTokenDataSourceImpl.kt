package com.devndev.lamp.data.datsource

import com.devndev.lamp.data.dto.request.GoogleTokenRequest
import com.devndev.lamp.data.dto.response.GoogleTokenResponse
import com.devndev.lamp.data.service.LoginService
import javax.inject.Inject

class GoogleTokenDataSourceImpl @Inject constructor(
    private val loginService: LoginService
) : GoogleTokenDataSource {
    override suspend fun getGoogleAuth(googleTokenRequest: GoogleTokenRequest): GoogleTokenResponse {
        return loginService.getGoogleAuth(googleTokenRequest)
    }
}
