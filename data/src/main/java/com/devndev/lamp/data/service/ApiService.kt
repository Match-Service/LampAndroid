package com.devndev.lamp.data.service

import com.devndev.lamp.domain.model.Item
import retrofit2.http.GET

interface ApiService {
    @GET("items")
    suspend fun getItems(): List<Item>
}
