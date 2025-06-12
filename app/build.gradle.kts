plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.hilt)
    alias(libs.plugins.ksp)
    // Firebase
    alias(libs.plugins.firebase.crashlytics)
    alias(libs.plugins.firebase.perf)
    // Secrets
    alias(libs.plugins.secrets.gradle.plugin)
    alias(libs.plugins.compose.compiler)
}

android {
    namespace = "com.cattailsw.retrl.app" // Changed
    compileSdk = 36 // Or your target SDK

    defaultConfig {
        applicationId = "com.cattailsw.retrl" // Changed
        minSdk = 26
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "com.cattailsw.retrl.CustomTestRunner" // Changed
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = true // Enable minification for release
            isShrinkResources = true // Shrink resources for release
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            // Optionally, specify signing configs for release builds
            // signingConfig signingConfigs.getByName("release")
        }
        debug {
            // Optionally, configure debug builds (e.g., applicationIdSuffix)
            applicationIdSuffix = ".debug"
            isMinifyEnabled = false // Typically, disable minification for debug
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true // Enable Jetpack Compose
        // buildConfig = true // Enable BuildConfig generation if needed
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    // Disable resource shrinking for now, re-enable if needed
    // shrinkResources = false
}

dependencies {
    // Project Modules
    implementation(project(":core:domain"))
    implementation(project(":core:data"))
    implementation(project(":core:ui"))
    implementation(project(":feature:editor"))
    implementation(project(":feature:export"))

    // Core Android & Jetpack
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.compose)
    implementation(libs.androidx.activity.compose)

    // Compose BOM and UI Toolkit
    implementation(platform(libs.compose.bom))
    implementation(libs.compose.ui)
    implementation(libs.compose.ui.graphics)
    implementation(libs.compose.ui.tooling.preview)
    implementation(libs.material3) // Material Design 3 (Jetpack Compose)
    implementation(libs.material)  // Material Design 2 (for XML themes and components)

    // Navigation
    implementation(libs.androidx.navigation.compose)

    // Hilt for Dependency Injection
    implementation(libs.hilt.android)
    ksp(libs.hilt.android.compiler)
    implementation(libs.androidx.hilt.navigation.compose) // Hilt integration for Compose Navigation

    // Coroutines for asynchronous programming
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.coroutines.android)

    // DataStore for preferences
    implementation(libs.androidx.dataStore.preferences)

    // Coil for image loading
    implementation(libs.coil.kt)
    implementation(libs.coil.kt.compose)

    // Firebase
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.analytics) // Analytics
    implementation(libs.firebase.crashlytics) // Crash reporting
    implementation(libs.firebase.performance) // Performance monitoring

    // Testing
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext)
    androidTestImplementation(libs.androidx.test.espresso.core)
    androidTestImplementation(platform(libs.compose.bom)) // Align Compose versions for tests
    androidTestImplementation(libs.compose.ui.test) // For Compose UI testing
    debugImplementation(libs.compose.ui.tooling) // For Compose tooling like preview
    debugImplementation(libs.compose.ui.test.manifest) // For Compose test manifest

    // Profile Installer (Optional, for performance optimization)
    implementation(libs.androidx.profileinstaller)
}

// Ensure KSP is correctly configured if used for other libraries beyond Hilt
// tasks.withType<com.google.devtools.ksp.gradle.KspTask>().configureEach {
//    // Configure KSP options if needed
// }