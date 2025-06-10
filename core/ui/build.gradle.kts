plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.example.ui" // Added namespace
    compileSdk = 34 // Added compileSdk

    defaultConfig {
        minSdk = 24 // Added minSdk
        // testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner" // Optional: if you have tests
        // consumerProguardFiles("consumer-rules.pro") // Optional
    }

    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1" // Ensure this matches your Kotlin version compatibility
    }
}

dependencies {
    api(project(":core:domain")) // Already there

    implementation("androidx.core:core-ktx:1.12.0") // Or a recent version
    implementation("androidx.compose.ui:ui:1.6.0") // Use recent compose versions
    implementation("androidx.compose.material3:material3:1.2.0")
    implementation("androidx.compose.ui:ui-tooling-preview:1.6.0")
    // Add other Compose dependencies as needed, e.g., graphics, foundation

    // Integration with activities
    implementation("androidx.activity:activity-compose:1.8.0")
}
