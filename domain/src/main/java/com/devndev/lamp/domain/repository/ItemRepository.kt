package com.devndev.lamp.domain.repository

import com.devndev.lamp.domain.model.Item

interface ItemRepository {
    suspend fun getItems(): List<Item>
}
