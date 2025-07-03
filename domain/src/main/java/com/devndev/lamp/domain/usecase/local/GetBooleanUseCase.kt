package com.devndev.lamp.domain.usecase.local

import com.devndev.lamp.domain.repository.LocalRepository
import javax.inject.Inject

class GetBooleanUseCase @Inject constructor(
    private val localRepository: LocalRepository
) {
    suspend operator fun invoke(key: String, value: Boolean): Boolean {
        return localRepository.getBoolean(key, value)
    }
}
