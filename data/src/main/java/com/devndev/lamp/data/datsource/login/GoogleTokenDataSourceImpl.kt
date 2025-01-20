package com.devndev.lamp.data.datsource.login

import com.devndev.lamp.data.dto.request.login.GoogleTokenRequest
import com.devndev.lamp.data.dto.response.login.GoogleTokenResponse
import com.devndev.lamp.data.service.LoginService
import javax.inject.Inject

class GoogleTokenDataSourceImpl @Inject constructor(
    private val loginService: LoginService
) : GoogleTokenDataSource {
    override suspend fun getGoogleAuth(googleTokenRequest: GoogleTokenRequest): GoogleTokenResponse {
        return loginService.getGoogleAuth(googleTokenRequest)
    }
}
