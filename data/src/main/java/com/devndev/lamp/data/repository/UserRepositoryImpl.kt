package com.devndev.lamp.data.repository

import com.devndev.lamp.data.datsource.UserDataSource
import com.devndev.lamp.data.dto.request.ModifyUserRequest
import com.devndev.lamp.data.dto.response.toDomainModel
import com.devndev.lamp.domain.model.ModifyUserParam
import com.devndev.lamp.domain.model.MyInfoDomainModel
import com.devndev.lamp.domain.model.UserDomainModel
import com.devndev.lamp.domain.repository.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(private val userDataSource: UserDataSource) :
    UserRepository {
    override suspend fun searchUser(name: String): List<UserDomainModel> {
        val userResponseDtos = userDataSource.searchUser(name)
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
            alarmSetting = modifyUserParam.alarmSetting,
            bioQuestions = modifyUserParam.bioQuestions,
            pushToken = modifyUserParam.pushToken
        )
        val code = userDataSource.modifyUser(modifyUserRequest).code()
        return code == 200
    }

    override suspend fun getMyInfo(): MyInfoDomainModel {
        val myInfoResponse = userDataSource.getMyInfo()
        return myInfoResponse.toDomainModel()
    }
}
