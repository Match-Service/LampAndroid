package com.devndev.lamp.presentation.ui.common

object CreationScreen {
    const val PERSONNEL = 1
    const val REGION = 2
    const val MOOD = 3
    const val INTRODUCTION = 4
}

object RegistrationScreen {
    const val NAME = 1
    const val UNIVERSITY = 2
    const val GENDER = 3
    const val BIRTH = 4
    const val INFO = 5
    const val INSTAGRAM = 6
    const val PROFILE = 7
}

object ReviewScreen {
    const val LAMP = 0
    const val PERSONAL = 1
}

object SignUpScreen {
    const val CONSENT = 1
    const val EMAIL = 2
    const val PASSWORD = 3
}

object MoodScreen {
    const val SELECT = 1
    const val FUNNY = 2
    const val CASUAL = 3
    const val SERIOUS = 4
}

object EmailStatus {
    const val NONE = 0
    const val NORMAL = 1
    const val INVALID_EMAIL = 2
    const val INVALID_CERTIFICATION_NUMBER = 3
    const val TIME_OUT = 4
}

object PasswordStatus {
    const val NONE = 0
    const val INVALID_PASSWORD = 1
    const val CONFIRM_PASSWORD = 2
    const val INVALID_CONFIRM_PASSWORD = 3
    const val SUCCESS = 4
}

object MainScreenPage {
    const val HOME = 0
    const val CHATTING = 1
    const val MY_PAGE = 2
}

object InstagramAuth {
    const val NONE = 0
    const val BEFORE_AUTH = 1
    const val AUTH_SUCCESS = 2
    const val AUTH_FAIL = 3
}

object EmailLoginStatus {
    const val NONE = 0
    const val INVALID_EMAIL = 1
    const val INVALID_PASSWORD = 2
}

object ForgotPasswordScreen {
    const val INFO_INPUT = 0
    const val AUTH = 1
    const val CHANGE_PASSWORD = 2
}

object AccountStatus {
    const val NONE = 0
    const val NEW_ACCOUNT = 1
    const val SIGNED_IN_ACCOUNT = 2
}

object InstagramStep {
    const val NONE = 0
    const val VALID = 1
    const val INVALID = 2
}

object SearchStatus {
    const val NONE = 0
    const val USER_NOT_FOUNT = 1
    const val SEARCHING = 2
    const val SEARCHED = 3
}
