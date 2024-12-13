package com.devndev.lamp.manager

import android.content.ComponentName
import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import com.devndev.lamp.domain.manager.AppIconManager
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class AppIconManagerImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : AppIconManager {
    override fun updateAppIcon(gender: String) {
        Log.d("AppIconManage", "Setting icon for gender: $gender")

        setAppIcon(gender)
    }

    override fun saveIconPreference(gender: String) {
        // Save the selected icon in SharedPreferences
        val sharedPreferences = context.getSharedPreferences("AppPreferences", Context.MODE_PRIVATE)
        with(sharedPreferences.edit()) {
            putString("selected_icon", gender)
            apply()
        }
    }

    private fun setAppIcon(gender: String) {
        val packageManager = context.packageManager

        // Correct ComponentName with fully qualified class names
        val aliases = listOf(
            "com.devndev.lamp.presentation.main.MainActivity",
            "com.devndev.lamp.MainActivityMale",
            "com.devndev.lamp.MainActivityFemale"
        )

        // Disable all aliases first
        aliases.forEach { alias ->
            packageManager.setComponentEnabledSetting(
                ComponentName(context, alias),
                PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                PackageManager.DONT_KILL_APP
            )
        }

        // Enable the correct alias based on gender
        val targetAlias = when (gender) {
            "MALE" -> "com.devndev.lamp.MainActivityMale"
            "FEMALE" -> "com.devndev.lamp.MainActivityFemale"
            else -> "com.devndev.lamp.presentation.main.MainActivity"
        }

        packageManager.setComponentEnabledSetting(
            ComponentName(context, targetAlias),
            PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
            PackageManager.DONT_KILL_APP
        )
    }
}
