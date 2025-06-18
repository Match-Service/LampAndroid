package com.devndev.lamp.data.dto.request.lampmatch

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RejectVoteRequest(
    @Json(name = "lampSuggestionId")
    val lampSuggestionId: Int
)
