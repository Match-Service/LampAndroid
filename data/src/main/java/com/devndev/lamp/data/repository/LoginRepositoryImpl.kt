package com.devndev.lamp.data.repository

import com.devndev.lamp.data.datsource.GoogleTokenDataSource
import com.devndev.lamp.data.datsource.LocalDataSource
import com.devndev.lamp.data.dto.request.GoogleTokenRequest
import com.devndev.lamp.data.dto.response.GoogleTokenResponse
import com.devndev.lamp.data.dto.response.toDomainModel
import com.devndev.lamp.domain.model.GoogleTokenDomainModel
import com.devndev.lamp.domain.model.GoogleTokenParam
import com.devndev.lamp.domain.repository.LoginRepository
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(
    private val googleTokenDataSource: GoogleTokenDataSource,
    private val localDataSource: LocalDataSource
) : LoginRepository {
    override suspend fun getGoogleAuth(googleTokenParam: GoogleTokenParam): GoogleTokenDomainModel {
        // GoogleTokenRequest로 변환
        val googleTokenRequest = GoogleTokenRequest(idToken = googleTokenParam.idToken)

        // 실제 데이터 소스에서 구글 인증 정보를 가져옴
        val googleTokenResponse: GoogleTokenResponse = googleTokenDataSource.getGoogleAuth(googleTokenRequest)

        return googleTokenResponse.toDomainModel()
    }

    override fun getIsNeedSignOut(): Boolean {
        return localDataSource.getIsNeedSignOut()
    }

    override fun saveIsNeedSignOut(isNeedSignOut: Boolean) {
        localDataSource.saveIsNeedSignOut(isNeedSignOut)
    }

    override fun setToken(token: String) {
        localDataSource.setToken(token)
    }
}
