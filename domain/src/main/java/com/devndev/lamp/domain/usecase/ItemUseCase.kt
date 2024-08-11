package com.devndev.lamp.domain.usecase

import com.devndev.lamp.domain.model.Item
import com.devndev.lamp.domain.repository.ItemRepository
import javax.inject.Inject

class ItemUseCase @Inject constructor(private val itemRepository: ItemRepository) {
    suspend operator fun invoke(): List<Item> {
        return itemRepository.getItems()
    }
}
