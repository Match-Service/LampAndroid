package com.devndev.lamp.presentation.ui.utils

object IconStatusManager {
    private var iconStatus: String = "NONE"

    fun getIconStatus(): String {
        return iconStatus
    }

    fun setIconStatus(status: String) {
        iconStatus = status
    }
}
