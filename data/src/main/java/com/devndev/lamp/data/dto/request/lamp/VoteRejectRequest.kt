package com.devndev.lamp.data.dto.request.lamp

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class VoteRejectRequest(
    @Json(name = "lampSuggestionId") val lampSuggestionId: Int
)
