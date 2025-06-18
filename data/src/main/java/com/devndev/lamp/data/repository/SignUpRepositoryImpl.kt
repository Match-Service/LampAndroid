package com.devndev.lamp.data.repository

import com.devndev.lamp.data.datasource.signup.SignUpDataSource
import com.devndev.lamp.data.dto.request.signup.AlarmSetting
import com.devndev.lamp.data.dto.request.signup.BioQuestion
import com.devndev.lamp.data.dto.request.signup.SignUpAuthRequest
import com.devndev.lamp.data.dto.request.signup.SignUpRequest
import com.devndev.lamp.data.dto.request.signup.User
import com.devndev.lamp.data.dto.request.signup.ValidateInstagramRequest
import com.devndev.lamp.data.dto.request.signup.ValidateNameRequest
import com.devndev.lamp.data.dto.response.signup.toDomainModel
import com.devndev.lamp.domain.model.signup.ProfileImageDomainModel
import com.devndev.lamp.domain.model.signup.SignUpParam
import com.devndev.lamp.domain.model.signup.ValidateInstagramParam
import com.devndev.lamp.domain.model.signup.ValidateNameParam
import com.devndev.lamp.domain.repository.SignUpRepository
import okhttp3.MultipartBody
import retrofit2.Response
import javax.inject.Inject

class SignUpRepositoryImpl @Inject constructor(private val signUpDataSource: SignUpDataSource) :
    SignUpRepository {
    override suspend fun validateName(validateNameParam: ValidateNameParam): Boolean {
        val validateNameRequest = ValidateNameRequest(name = validateNameParam.name)
        val code = signUpDataSource.validateName(validateNameRequest).code()
        return code == 200
    }

    override suspend fun validateInstagram(validateInstagramParam: ValidateInstagramParam): Boolean {
        val validateInstagramRequest =
            ValidateInstagramRequest(instagramId = validateInstagramParam.instagramId)
        val code = signUpDataSource.validateInstagram(validateInstagramRequest).code()
//        return code == 200
        // 서버 미완성으로 인해 true 반환
        return true
    }

    override suspend fun uploadImages(files: List<MultipartBody.Part>): List<ProfileImageDomainModel> {
        val response = signUpDataSource.uploadImages(files)
        return response.toDomainModel()
    }

    override suspend fun signUp(signUpParam: SignUpParam): Response<Void> {
        val signUpRequest =
            SignUpRequest(
                signupAuthRequest = SignUpAuthRequest(signUpParam.signupAuthRequest.signUpToken),
                user = signUpParamToSignUpRequest(signUpParam)
            )
        val response = signUpDataSource.signUp(signUpRequest)
        return response
    }

    private fun signUpParamToSignUpRequest(signUpParam: SignUpParam): User {
        return User(
            name = signUpParam.user.name,
            job = signUpParam.user.job,
            jobName = signUpParam.user.jobName,
            gender = signUpParam.user.gender,
            birth = signUpParam.user.birth,
            instagramId = signUpParam.user.instagramId,
            bio = signUpParam.user.bio,
            profileImages = signUpParam.user.profileImages,
            alarmSetting = AlarmSetting(
                allPush = signUpParam.user.alarmSetting.allPush,
                lampInvite = signUpParam.user.alarmSetting.lampInvite,
                lampVisit = signUpParam.user.alarmSetting.lampVisit,
                newMatch = signUpParam.user.alarmSetting.newMatch,
                receiveBadge = signUpParam.user.alarmSetting.receiveBadge,
                receiveMessage = signUpParam.user.alarmSetting.receiveMessage
            ),
            bioQuestions = listOf(
                BioQuestion(
                    question = signUpParam.user.bioQuestions[0].question,
                    answer = signUpParam.user.bioQuestions[0].answer
                ),
                BioQuestion(
                    question = signUpParam.user.bioQuestions[1].question,
                    answer = signUpParam.user.bioQuestions[1].answer
                ),
                BioQuestion(
                    question = signUpParam.user.bioQuestions[2].question,
                    answer = signUpParam.user.bioQuestions[2].answer
                )
            ),
            pushToken = signUpParam.user.pushToken
        )
    }
}
