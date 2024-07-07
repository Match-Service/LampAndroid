package com.devndev.data.repository

import com.devndev.data.datsource.ItemDataSource
import com.devndev.domain.model.Item
import com.devndev.domain.repository.ItemRepository
import javax.inject.Inject

class ItemRepositoryImpl @Inject constructor(private val itemDataSource: ItemDataSource) :
    ItemRepository {
    override suspend fun getItems(): List<Item> {
        return itemDataSource.fetchItems()
    }
}
