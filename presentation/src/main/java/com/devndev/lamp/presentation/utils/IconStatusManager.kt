package com.devndev.lamp.presentation.utils

object IconStatusManager {
    private var iconStatus: String = "NONE"

    fun getIconStatus(): String {
        return iconStatus
    }

    fun setIconStatus(status: String) {
        iconStatus = status
    }
}
