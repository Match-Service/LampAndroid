package com.devndev.lamp.data.datsource

import com.devndev.lamp.domain.model.Item

interface ItemDataSource {
    suspend fun fetchItems(): List<Item>
}
