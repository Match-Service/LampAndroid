package com.devndev.lamp.data.repository

import com.devndev.lamp.data.datasource.lampmatch.LampMatchDataSource
import com.devndev.lamp.data.dto.request.lampmatch.AcceptVoteRequest
import com.devndev.lamp.data.dto.request.lampmatch.RejectVoteRequest
import com.devndev.lamp.domain.model.lampmatch.MatchSuggestionDomainModel
import com.devndev.lamp.domain.repository.LampMatchRepository
import javax.inject.Inject

class LampMatchRepositoryImpl @Inject constructor(
    private val lampMatchDataSource: LampMatchDataSource
) : LampMatchRepository {
    override suspend fun startMatch() {
        lampMatchDataSource.startMatch()
    }

    override suspend fun stopMatch() {
        lampMatchDataSource.stopMatch()
    }

    override suspend fun accept(lampSuggestionId: Int) {
        lampMatchDataSource.accept(acceptVoteRequest = AcceptVoteRequest(lampSuggestionId))
    }

    override suspend fun reject(lampSuggestionId: Int) {
        lampMatchDataSource.reject(rejectVoteRequest = RejectVoteRequest(lampSuggestionId))
    }

    override suspend fun getMatchSuggestion(): MatchSuggestionDomainModel {
        return lampMatchDataSource.getMatchSuggestion().toDomainModel()
    }
}
