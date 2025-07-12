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
    val gender: String,
    val owner: MatchSuggestionUserDomainModel,
    val participants: List<MatchSuggestionUserDomainModel>
)

data class MatchSuggestionUserDomainModel(
    val userId: Int,
    val name: String,
    val job: String?,
    val jobName: String?,
    val birth: String,
    val bio: String?,
    val instagramId: String?,
    val profileImageUrls: List<String>,
    val individuality: IndividualityDomainModel?,
    val bioQuestion: List<BioDomainModel>
)

data class IndividualityDomainModel(
    val attractiveness: Int,
    val personality: Int,
    val voice: Int,
    val fashion: Int,
    val conversation: Int
)

data class BioDomainModel(
    val question: String,
    val answer: String
)
