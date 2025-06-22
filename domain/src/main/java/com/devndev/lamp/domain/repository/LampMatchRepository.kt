package com.devndev.lamp.domain.repository

import com.devndev.lamp.domain.model.lamp.AcceptVoteParam
import com.devndev.lamp.domain.model.lamp.RejectVoteParam
import com.devndev.lamp.domain.model.lampmatch.MatchSuggestionDomainModel

interface LampMatchRepository {
    suspend fun startMatch()
    suspend fun stopMatch()
    suspend fun accept(acceptVoteParam: AcceptVoteParam)
    suspend fun reject(rejectVoteParam: RejectVoteParam)
    suspend fun getMatchSuggestion(): MatchSuggestionDomainModel
}
