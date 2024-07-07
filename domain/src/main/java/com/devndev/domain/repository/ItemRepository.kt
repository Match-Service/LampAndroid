package com.devndev.domain.repository

import com.devndev.domain.model.Item

interface ItemRepository {
    suspend fun getItems(): List<Item>
}
