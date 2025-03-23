package com.devndev.lamp.domain.model.lampmatch

data class MatchSuggestionDomainModel(
    val lampSuggestionId: Int,
    val lampId: Int,
    val name: String,
    val description: String,
    val url: String,
    val hopeMatchNumber: Int,
    val location: String,
    val color: String,
    val approveCount: Int,
    val rejectCount: Int,
    val matchCompleteTime: String,
    val owner: MatchSuggestionUserDomainModel,
    val participants: List<MatchSuggestionUserDomainModel>
)

data class MatchSuggestionUserDomainModel(
    val userId: Int,
    val name: String,
    val profileImageUrl: String,
    val individuality: IndividualityDomainModel?
)

data class IndividualityDomainModel(
    val attractiveness: Int,
    val personality: Int,
    val voice: Int,
    val fashion: Int,
    val conversation: Int
)
