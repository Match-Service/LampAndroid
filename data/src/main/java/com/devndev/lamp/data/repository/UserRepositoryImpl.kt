package com.devndev.lamp.data.repository

import android.util.Log
import com.devndev.lamp.data.datsource.user.UserDataSource
import com.devndev.lamp.data.dto.request.signup.AlarmSetting
import com.devndev.lamp.data.dto.request.signup.BioQuestion
import com.devndev.lamp.data.dto.request.user.EditImageRequest
import com.devndev.lamp.data.dto.request.user.ModifyUserRequest
import com.devndev.lamp.data.dto.request.user.PushTokenRequest
import com.devndev.lamp.data.dto.response.user.toDomainModel
import com.devndev.lamp.domain.model.user.EditImageParam
import com.devndev.lamp.domain.model.user.ModifyUserParam
import com.devndev.lamp.domain.model.user.MyInfoDomainModel
import com.devndev.lamp.domain.model.user.PushTokenParam
import com.devndev.lamp.domain.model.user.UserDomainModel
import com.devndev.lamp.domain.model.user.UserStatusDomainModel
import com.devndev.lamp.domain.repository.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(private val userDataSource: UserDataSource) :
    UserRepository {
    override suspend fun searchInviteUser(name: String): List<UserDomainModel> {
        val userResponseDtos = userDataSource.searchInviteUser(name)
        return userResponseDtos.toDomainModel()
    }

    override suspend fun searchVisitUser(name: String): List<UserDomainModel> {
        val userResponseDtos = userDataSource.searchVisitUser(name)
        return userResponseDtos.toDomainModel()
    }

    //    override suspend fun modifyUser(modifyUserParam: ModifyUserParam): Response<Void> {
    override suspend fun modifyUser(modifyUserParam: ModifyUserParam): Boolean {
        val modifyUserRequest = ModifyUserRequest(
            name = modifyUserParam.name,
            job = modifyUserParam.job,
            jobName = modifyUserParam.jobName,
            gender = modifyUserParam.gender,
            birth = modifyUserParam.birth,
            instagramId = modifyUserParam.instagramId,
            bio = modifyUserParam.bio,
            profileImages = modifyUserParam.profileImages,
            alarmSetting = AlarmSetting(
                allPush = modifyUserParam.alarmSetting.allPush,
                lampInvite = modifyUserParam.alarmSetting.lampInvite,
                lampVisit = modifyUserParam.alarmSetting.lampVisit,
                newMatch = modifyUserParam.alarmSetting.newMatch,
                receiveBadge = modifyUserParam.alarmSetting.receiveBadge,
                receiveMessage = modifyUserParam.alarmSetting.receiveMessage
            ),
            bioQuestions = listOf(
                BioQuestion(
                    question = modifyUserParam.bioQuestions[0].question,
                    answer = modifyUserParam.bioQuestions[0].answer
                ),
                BioQuestion(
                    question = modifyUserParam.bioQuestions[1].question,
                    answer = modifyUserParam.bioQuestions[1].answer
                ),
                BioQuestion(
                    question = modifyUserParam.bioQuestions[2].question,
                    answer = modifyUserParam.bioQuestions[2].answer
                )
            ),
            pushToken = modifyUserParam.pushToken
        )
        val code = userDataSource.modifyUser(modifyUserRequest).code()
        return code == 200
    }

//    override suspend fun editImage(files: List<MultipartBody.Part>): List<ProfileImageDomainModel> {
//        val response = userDataSource.editImage(files)
//        return response.toDomainModel()
//    }
    override suspend fun editImage(files: EditImageParam): Boolean {
        val response = userDataSource.editImage(EditImageRequest(files.profileImages)).code()
        return response == 200
    }

    override suspend fun getMyInfo(): MyInfoDomainModel {
        val myInfoResponse = userDataSource.getMyInfo()
        return myInfoResponse.toDomainModel()
    }

    override suspend fun putPushToken(pushTokenParam: PushTokenParam) {
        try {
            val response =
                userDataSource.putPushToken(PushTokenRequest(pushToken = pushTokenParam.pushToken))
            if (response.isSuccessful) {
                Log.d("PushToken", "putPushToken Success pushToken: ${pushTokenParam.pushToken}")
            } else {
                Log.e("PushToken", "putPushToken Fail: ${response.code()} - ${response.message()}")
            }
        } catch (e: Exception) {
            Log.e("PushToken", "putPushToken Error", e)
        }
    }

    override suspend fun getUserStatus(): UserStatusDomainModel {
        return userDataSource.getUserStatus().toDomainModel()
    }
}
