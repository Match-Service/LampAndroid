import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties
import com.devndev.lamp.buildsrc.AppConfig

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.kotlin.ksp)
    alias(libs.plugins.kotlin.kapt)
    alias(libs.plugins.hilt)
}

android {
    namespace = AppConfig.dataNameSpace
    compileSdk = AppConfig.compileSdk

    defaultConfig {
        minSdk = AppConfig.minSdk

        testInstrumentationRunner = AppConfig.testRunner
        consumerProguardFiles("consumer-rules.pro")

        buildConfigField(
            type = "String",
            name = "GOOGLE_CLIENT_ID",
            value = "\"${
            gradleLocalProperties(
                rootDir,
                providers
            ).getProperty("google_client_id")
            }\""
        )
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = AppConfig.sourceCompatibility
        targetCompatibility = AppConfig.targetCompatibility
    }
    kotlinOptions {
        jvmTarget = AppConfig.jvmTarget
    }
}

dependencies {
    implementation(project(":domain"))
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    implementation(libs.google.login)

    implementation(libs.retrofit)
    implementation(libs.retrofit.moshi)

    implementation(libs.moshi.kotlin)
    ksp(libs.moshi.code.gen)

    implementation(libs.hilt)
    kapt(libs.hilt.compiler)
}
