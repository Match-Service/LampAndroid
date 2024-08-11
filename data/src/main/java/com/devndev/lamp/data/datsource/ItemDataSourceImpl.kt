package com.devndev.lamp.data.datsource

import com.devndev.lamp.data.api.ApiService
import com.devndev.lamp.domain.model.Item
import javax.inject.Inject

class ItemDataSourceImpl @Inject constructor(private val apiService: ApiService) : ItemDataSource {
    override suspend fun fetchItems(): List<Item> {
        // 실제로 네트워크를 통해 데이터를 가져오는 로직
        return apiService.getItems()
    }
}
