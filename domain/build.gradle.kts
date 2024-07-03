import com.devndev.lamp.buildsrc.AppConfig

plugins {
    id("kotlin")
    alias(libs.plugins.jetbrainsKotlinJvm)
    alias(libs.plugins.kotlin.kapt)
}

java {
    sourceCompatibility = AppConfig.sourceCompatibility
    targetCompatibility = AppConfig.targetCompatibility
}

dependencies {
    implementation(libs.hilt.core)
    kapt(libs.hilt.compiler)
    implementation(libs.coroutine.core)
}
