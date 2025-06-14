package com.devndev.lamp.domain.model.chat

enum class MessageType(val value: String) {
    MESSAGE("MESSAGE"),
    CREATE_APPOINTMENT("CREATE_APPOINTMENT"),
    READY_APPOINTMENT("READY_APPOINTMENT"),
    ALL_READY_APPOINTMENT("ALL_READY_APPOINTMENT"),
    RETRY_APPOINTMENT("RETRY_APPOINTMENT"),
    CONFIRM_APPOINTMENT("CONFIRM_APPOINTMENT");

    companion object {
        fun fromString(value: String): MessageType? {
            return entries.find { it.value == value }
        }
    }
}
