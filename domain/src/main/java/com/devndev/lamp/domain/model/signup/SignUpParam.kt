package com.devndev.lamp.domain.model.signup

data class SignUpParam(
    val signupAuthRequest: SignUpAuthRequest,
    val user: User
)

data class SignUpAuthRequest(
    val signUpToken: String
)

data class User(
    val name: String,
    val job: String,
    val jobName: String,
    val gender: String,
    val birth: String,
    val instagramId: String,
    val bio: String,
    var profileImages: List<String>,
    val alarmSetting: AlarmSetting,
    val bioQuestions: List<BioQuestion>,
    var pushToken: String
)

data class AlarmSetting(
    val allPush: Boolean,
    val lampInvite: Boolean,
    val lampVisit: Boolean,
    val newMatch: Boolean,
    val receiveAssessment: Boolean,
    val receiveMessage: Boolean
)

data class BioQuestion(
    val question: String,
    val answer: String
)
