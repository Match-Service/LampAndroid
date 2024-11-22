package com.devndev.lamp.data.repository

import com.devndev.lamp.data.datsource.GoogleTokenDataSource
import com.devndev.lamp.data.dto.request.GoogleTokenRequest
import com.devndev.lamp.data.dto.response.GoogleTokenResponse
import com.devndev.lamp.data.dto.response.toDomainModel
import com.devndev.lamp.domain.model.GoogleTokenDomainModel
import com.devndev.lamp.domain.model.GoogleTokenParam
import com.devndev.lamp.domain.repository.GoogleTokenRepository
import javax.inject.Inject

class GoogleTokenRepositoryImpl @Inject constructor(
    private val googleTokenDataSource: GoogleTokenDataSource
) : GoogleTokenRepository {
    override suspend fun getGoogleAuth(googleTokenParam: GoogleTokenParam): GoogleTokenDomainModel {
        // GoogleTokenRequest로 변환
        val googleTokenRequest = GoogleTokenRequest(idToken = googleTokenParam.idToken)

        // 실제 데이터 소스에서 구글 인증 정보를 가져옴
        val googleTokenResponse: GoogleTokenResponse = googleTokenDataSource.getGoogleAuth(googleTokenRequest)

        return googleTokenResponse.toDomainModel()
    }
}
