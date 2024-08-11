package com.devndev.lamp.presentation.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devndev.lamp.domain.model.Item
import com.devndev.lamp.domain.usecase.ItemUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val itemUseCase: ItemUseCase
) : ViewModel() {

    private val _items = MutableStateFlow<List<Item>>(emptyList())
    val items: StateFlow<List<Item>> = _items

    init {
        fetchData()
    }

    private fun fetchData() {
        viewModelScope.launch {
            _items.value = itemUseCase()
        }
    }
}
