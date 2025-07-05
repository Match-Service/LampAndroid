package com.devndev.lamp.data.service

import com.devndev.lamp.data.dto.request.assessment.AssessmentRequest
import com.devndev.lamp.data.dto.response.assessment.AssessmentListResponse
import com.devndev.lamp.data.dto.response.assessment.AssessmentResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface AssessmentService {
    @GET("api/v1/assessment/target/{lampMatchId}")
    suspend fun getAssessment(
        @Path("lampMatchId") lampMatchId: Int
    ): AssessmentResponse

    @GET("api/v1/assessment/target")
    suspend fun getAssessmentList(): List<AssessmentListResponse>

    @POST("api/v1/assessment")
    suspend fun assessment(
        @Body assessmentRequest: AssessmentRequest
    )
}
