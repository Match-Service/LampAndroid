package com.devndev.lamp.data.dto.response.lampmatch

import com.devndev.lamp.domain.model.lampmatch.BioDomainModel
import com.devndev.lamp.domain.model.lampmatch.IndividualityDomainModel
import com.devndev.lamp.domain.model.lampmatch.MatchSuggestionDomainModel
import com.devndev.lamp.domain.model.lampmatch.MatchSuggestionUserDomainModel
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MatchSuggestionResponse(
    @Json(name = "lampSuggestionId")
    val lampSuggestionId: Int,
    @Json(name = "lampId")
    val lampId: Int,
    @Json(name = "name")
    val name: String,
    @Json(name = "description")
    val description: String,
    @Json(name = "url")
    val url: String,
    @Json(name = "hopeMatchNumber")
    val hopeMatchNumber: Int,
    @Json(name = "location")
    val location: String,
    @Json(name = "color")
    val color: String,
    @Json(name = "approveCount")
    val approveCount: Int,
    @Json(name = "rejectCount")
    val rejectCount: Int,
    @Json(name = "matchCompleteTime")
    val matchCompleteTime: String,
    @Json(name = "gender")
    val gender: String,
    @Json(name = "owner")
    val owner: MatchSuggestionUser,
    @Json(name = "participants")
    val participants: List<MatchSuggestionUser>
) {
    fun toDomainModel(): MatchSuggestionDomainModel {
        return MatchSuggestionDomainModel(
            lampSuggestionId = lampSuggestionId,
            lampId = lampId,
            name = name,
            description = description,
            url = url,
            hopeMatchNumber = hopeMatchNumber,
            location = location,
            color = color,
            approveCount = approveCount,
            rejectCount = rejectCount,
            matchCompleteTime = matchCompleteTime,
            gender = gender,
            owner = owner.toDomainModel(),
            participants = participants.map { it.toDomainModel() }
        )
    }
}

@JsonClass(generateAdapter = true)
data class MatchSuggestionUser(
    @Json(name = "userId")
    val userId: Int,
    @Json(name = "name")
    val name: String,
    @Json(name = "job")
    val job: String?,
    @Json(name = "jobName")
    val jobName: String?,
    @Json(name = "birth")
    val birth: String,
    @Json(name = "bio")
    val bio: String?,
    @Json(name = "instagramId")
    val instagramId: String?,
    @Json(name = "profileImageUrls")
    val profileImageUrls: List<String>,
    @Json(name = "individuality")
    val individuality: Individuality?,
    @Json(name = "bioQuestion")
    val bioQuestion: List<BioQuestion>
) {
    fun toDomainModel(): MatchSuggestionUserDomainModel {
        return MatchSuggestionUserDomainModel(
            userId = userId,
            name = name,
            job = job.orEmpty(),
            jobName = jobName.orEmpty(),
            birth = birth,
            bio = bio.orEmpty(),
            instagramId = instagramId.orEmpty(),
            profileImageUrls = profileImageUrls,
            individuality = individuality?.toDomainModel(),
            bioQuestion = bioQuestion.map { it.toDomainModel() }
        )
    }
}

@JsonClass(generateAdapter = true)
data class BioQuestion(
    @Json(name = "question")
    val question: String,
    @Json(name = "answer")
    val answer: String
) {
    fun toDomainModel(): BioDomainModel {
        return BioDomainModel(
            question = question,
            answer = answer
        )
    }
}

@JsonClass(generateAdapter = true)
data class Individuality(
    @Json(name = "attractiveness")
    val attractiveness: Int,
    @Json(name = "personality")
    val personality: Int,
    @Json(name = "voice")
    val voice: Int,
    @Json(name = "fashion")
    val fashion: Int,
    @Json(name = "conversation")
    val conversation: Int
) {
    fun toDomainModel(): IndividualityDomainModel {
        return IndividualityDomainModel(
            attractiveness = attractiveness,
            personality = personality,
            voice = voice,
            fashion = fashion,
            conversation = conversation
        )
    }
}
