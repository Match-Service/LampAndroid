package com.devndev.lamp.data.repository

import com.devndev.lamp.data.datsource.ItemDataSource
import com.devndev.lamp.domain.model.Item
import com.devndev.lamp.domain.repository.ItemRepository
import javax.inject.Inject

class ItemRepositoryImpl @Inject constructor(private val itemDataSource: ItemDataSource) :
    ItemRepository {
    override suspend fun getItems(): List<Item> {
        return itemDataSource.fetchItems()
    }
}
