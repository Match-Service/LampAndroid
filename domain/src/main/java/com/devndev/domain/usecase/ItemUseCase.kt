package com.devndev.domain.usecase

import com.devndev.domain.model.Item
import com.devndev.domain.repository.ItemRepository
import javax.inject.Inject

class ItemUseCase @Inject constructor(private val itemRepository: ItemRepository) {
    suspend operator fun invoke(): List<Item> {
        return itemRepository.getItems()
    }
}
