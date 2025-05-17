package com.devndev.lamp.domain.eventbus

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.filterIsInstance

object AlarmEventBus {
    private val _events = MutableSharedFlow<Unit>()
    val events = _events.asSharedFlow()

    suspend fun postEvent() {
        _events.emit(Unit)
    }

    suspend inline fun <reified T> subscribeEvent(crossinline onEvent: (T) -> Unit) {
        events.filterIsInstance<T>().collect {
            onEvent(it)
        }
    }
}
