package com.devndev.lamp.data.repository

import com.devndev.lamp.data.datsource.setting.SettingDataSource
import com.devndev.lamp.data.dto.request.setting.PushSettingRequest
import com.devndev.lamp.data.dto.response.setting.toDomainModel
import com.devndev.lamp.domain.model.setting.PushSettingDomainModel
import com.devndev.lamp.domain.model.setting.PushSettingParam
import com.devndev.lamp.domain.repository.SettingRepository
import javax.inject.Inject

class SettingRepositoryImpl @Inject constructor(
    private val settingDataSource: SettingDataSource
) : SettingRepository {
    override suspend fun getPushSetting(): PushSettingDomainModel {
        return settingDataSource.getPushSetting().toDomainModel()
    }

    override suspend fun putPushSetting(pushSettingParam: PushSettingParam) {
        settingDataSource.putPushSetting(
            PushSettingRequest(
                allPush = pushSettingParam.allPush,
                lampInvite = pushSettingParam.lampInvite,
                lampVisit = pushSettingParam.lampVisit,
                newMatch = pushSettingParam.newMatch,
                receiveAssessment = pushSettingParam.receiveAssessment,
                receiveMessage = pushSettingParam.receiveMessage
            )
        )
    }
}
