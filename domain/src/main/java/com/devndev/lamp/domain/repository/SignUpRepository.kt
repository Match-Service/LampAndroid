package com.devndev.lamp.domain.repository

import com.devndev.lamp.domain.model.ValidateInstagramParam
import com.devndev.lamp.domain.model.ValidateNameParam

interface SignUpRepository {
    suspend fun validateName(validateNameParam: ValidateNameParam): Boolean
    suspend fun validateInstagram(validateInstagramParam: ValidateInstagramParam): Boolean
}
