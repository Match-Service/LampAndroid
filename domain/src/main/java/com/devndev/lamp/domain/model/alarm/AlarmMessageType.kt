package com.devndev.lamp.domain.model.alarm

enum class AlarmMessageType(val raw: String) {
    INVITE_REQUEST("INVITE_REQUEST"),
    INVITE_ACCEPT("INVITE_ACCEPT"),
    INVITE_REJECT("INVITE_REJECT"),
    VISIT_REQUEST("VISIT_REQUEST"),
    VISIT_ACCEPT("VISIT_ACCEPT"),
    VISIT_REJECT("VISIT_REJECT"),
    CHAT("CHAT"),
    LAMP_SUGGESTION("LAMP_SUGGESTION"),
    ASSESSMENT("ASSESSMENT");

    companion object {
        fun from(raw: String?): AlarmMessageType? {
            return entries.firstOrNull { it.raw == raw }
        }
    }
}
