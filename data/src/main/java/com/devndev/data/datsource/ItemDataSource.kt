package com.devndev.data.datsource

import com.devndev.domain.model.Item

interface ItemDataSource {
    suspend fun fetchItems(): List<Item>
}
