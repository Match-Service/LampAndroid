package com.devndev.lamp.domain.usecase.local

import com.devndev.lamp.domain.repository.LocalRepository
import javax.inject.Inject

class GetLongUseCase @Inject constructor(
    private val localRepository: LocalRepository
) {
    suspend operator fun invoke(key: String, value: Long): Long {
        return localRepository.getLong(key, value)
    }
}
