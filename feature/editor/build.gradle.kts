plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    // id("kotlin-kapt") // If using Hilt and need it for this module
    // id("dagger.hilt.android.plugin") // If using Hilt
}

android {
    namespace = "com.example.editor"
    compileSdk = 34 // Or your project's compileSdk

    defaultConfig {
        minSdk = 24 // Or your project's minSdk
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1" // Match project's version
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation(project(":core:domain"))
    implementation(project(":core:ui"))
    implementation(project(":core:sound"))

    // AndroidX & Compose
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.7.0")
    implementation("androidx.activity:activity-compose:1.8.0")

    implementation(platform("androidx.compose:compose-bom:2023.08.00")) // Check for latest BOM
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")

    // ViewModel
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.7.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.7.0")

    // Hilt (if you plan to use it for ViewModels here)
    // implementation("com.google.dagger:hilt-android:2.48") // Check latest
    // kapt("com.google.dagger:hilt-compiler:2.48")
    // implementation("androidx.hilt:hilt-navigation-compose:1.1.0") // Check latest

    // Coroutines for ViewModelScope
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3") // Should be brought by domain, but can be explicit

    // Javax Inject (often needed with Hilt or Dagger)
    implementation("javax.inject:javax.inject:1")
}
