package com.devndev.lamp.data.repository

import android.util.Log
import com.devndev.lamp.data.datsource.lampmatach.LampMatchDataSource
import com.devndev.lamp.data.dto.request.lamp.VoteAcceptRequest
import com.devndev.lamp.data.dto.request.lamp.VoteRejectRequest
import com.devndev.lamp.data.repository.LampRepositoryImpl.Companion.TAG
import com.devndev.lamp.domain.model.lamp.AcceptVoteParam
import com.devndev.lamp.domain.model.lamp.RejectVoteParam
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

    override suspend fun accept(acceptVoteParam: AcceptVoteParam) {
        val acceptVoteRequest = VoteAcceptRequest(
            lampSuggestionId = acceptVoteParam.lampSuggestionId
        )

        val response = lampMatchDataSource.accept(acceptVoteRequest)

        if (response.isSuccessful) {
            Log.d(TAG, "AcceptVote successfully, Status Code: ${response.code()}")
        } else {
            Log.e(
                TAG,
                "Failed to AcceptVote, Status Code: ${response.code()} ${response.raw()}"
            )
        }
    }

    override suspend fun reject(rejectVoteParam: RejectVoteParam) {
        val rejectVoteRequest = VoteRejectRequest(
            lampSuggestionId = rejectVoteParam.lampSuggestionId
        )

        val response = lampMatchDataSource.reject(rejectVoteRequest)

        if (response.isSuccessful) {
            Log.d(TAG, "RejectVote successfully, Status Code: ${response.code()}")
        } else {
            Log.e(
                TAG,
                "Failed to RejectVote, Status Code: ${response.code()} ${response.raw()}"
            )
        }
    }

    override suspend fun getMatchSuggestion(): MatchSuggestionDomainModel {
        return lampMatchDataSource.getMatchSuggestion().toDomainModel()
    }
}
