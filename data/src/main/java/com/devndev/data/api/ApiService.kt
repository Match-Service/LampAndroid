package com.devndev.data.api

import com.devndev.domain.model.Item
import retrofit2.http.GET

interface ApiService {
    @GET("items")
    suspend fun getItems(): List<Item>
}
