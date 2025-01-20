package com.devndev.lamp.data.repository

import com.devndev.lamp.data.datsource.local.LocalDataSource
import com.devndev.lamp.data.datsource.login.GoogleTokenDataSource
import com.devndev.lamp.data.dto.request.login.GoogleTokenRequest
import com.devndev.lamp.data.dto.response.login.GoogleTokenResponse
import com.devndev.lamp.data.dto.response.login.toDomainModel
import com.devndev.lamp.domain.model.login.GoogleTokenDomainModel
import com.devndev.lamp.domain.model.login.GoogleTokenParam
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
