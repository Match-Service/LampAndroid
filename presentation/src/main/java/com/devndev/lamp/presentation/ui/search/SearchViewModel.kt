package com.devndev.lamp.presentation.ui.search

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devndev.lamp.domain.model.Item
import com.devndev.lamp.domain.model.UserDomainModel
import com.devndev.lamp.domain.usecase.ItemUseCase
import com.devndev.lamp.domain.usecase.SearchUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val itemUseCase: ItemUseCase,
    private val searchUserUseCase: SearchUserUseCase
) : ViewModel() {

    private val _items = MutableStateFlow<List<Item>>(emptyList())
    val items: StateFlow<List<Item>> = _items

    private val _users = mutableStateListOf<UserDomainModel>()
    val users: List<UserDomainModel> = _users

    init {
        fetchData()
    }

    fun searchUsers(name: String) {
        viewModelScope.launch {
            _users.clear()
            _users.addAll(searchUserUseCase(name))
        }
    }

    fun resetUsers() {
        _users.clear()
    }

    private fun fetchData() {
        viewModelScope.launch {
            _items.value = itemUseCase()
        }
    }
}
