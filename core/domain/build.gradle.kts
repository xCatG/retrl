plugins {
    alias(libs.plugins.android.library)      // Changed from kotlin.jvm to android.library
    alias(libs.plugins.kotlin.android)       // Added kotlin.android for Android support
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.cattailsw.retrl.core.domain" // Added namespace
    compileSdk = 36 // Or your project's compileSdk, taken from app module

    defaultConfig {
        minSdk = 24 // Or your project's minSdk, taken from core:data
        // consumerProguardFiles("consumer-rules.pro") // Optional: add if this lib should provide proguard rules
    }

    // Ensure JVM target consistency, now handled by compileOptions and kotlinOptions
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    // buildFeatures { // Not needed if no compose/databinding etc. in domain
    //     compose = true
    // }
}

dependencies {
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.serialization.json)
    // Test dependencies (optional, but good practice)
    // testImplementation(libs.junit)
    // testImplementation(libs.kotlinx.coroutines.test)
}
