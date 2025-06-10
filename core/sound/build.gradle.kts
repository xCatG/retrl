plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.example.sound"
    compileSdk = 34 // Use a recent SDK

    defaultConfig {
        minSdk = 24 // Example minSdk
        // testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner" // If adding tests
    }
    // ... other settings like compileOptions, kotlinOptions
}

dependencies {
    implementation("javax.inject:javax.inject:1")
    // No other specific app module dependencies as per architecture
    // implementation("androidx.core:core-ktx:1.12.0") // Might be useful for context extensions
}
