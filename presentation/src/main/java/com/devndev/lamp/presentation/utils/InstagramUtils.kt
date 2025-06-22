package com.devndev.lamp.presentation.utils

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import androidx.core.net.toUri

object InstagramUtils {
    fun openInstagramProfile(context: Context, instagramId: String) {
        val appUri = "http://instagram.com/_u/$instagramId".toUri()
        val appIntent = Intent(Intent.ACTION_VIEW, appUri).apply {
            setPackage("com.instagram.android")
        }

        try {
            context.startActivity(appIntent)
        } catch (e: ActivityNotFoundException) {
            // 앱이 없을 경우 웹으로 fallback
            val webUri = "http://instagram.com/$instagramId".toUri()
            val webIntent = Intent(Intent.ACTION_VIEW, webUri)
            context.startActivity(webIntent)
        }
    }
}
