package com.devndev.lamp.domain.usecase.lampmatch

import com.devndev.lamp.domain.model.lampmatch.MatchSuggestionDomainModel
import com.devndev.lamp.domain.repository.LampMatchRepository
import javax.inject.Inject

class GetMatchSuggestionUseCase @Inject constructor(
    private val lampMatchRepository: LampMatchRepository
) {
    suspend operator fun invoke(): MatchSuggestionDomainModel {
        return lampMatchRepository.getMatchSuggestion()
    }
}
