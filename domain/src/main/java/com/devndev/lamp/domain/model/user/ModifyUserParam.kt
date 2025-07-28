package com.devndev.lamp.domain.model.user

import com.devndev.lamp.domain.model.signup.BioQuestion

data class ModifyUserParam(
    val name: String,
    val job: String,
    val jobName: String,
    val gender: String,
    val birth: String,
    val instagramId: String,
    val bio: String,
    var profileImages: List<String>,
    val bioQuestions: List<BioQuestion>,
    var pushToken: String
)

data class EditImageParam(
    val profileImages: List<String?>
)
