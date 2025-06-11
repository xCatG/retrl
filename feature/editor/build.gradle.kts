plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.hilt)
    alias(libs.plugins.ksp)
    alias(libs.plugins.compose.compiler) // For Hilt
}

android {
    namespace = "com.cattailsw.retrl.feature.editor" // **Ensure this is correct**
    compileSdk = 34 // Or your target SDK

    defaultConfig {
        minSdk = 24
        // targetSdk = 34 // Not typically set in library modules
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false // Or true, with appropriate Proguard rules
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        debug {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true // This feature module is UI-heavy
    }
}

dependencies {
    // Project Dependencies
    implementation(project(":core:domain"))
    implementation(project(":core:ui"))
    implementation(project(":core:sound"))

    // Hilt for Dependency Injection
    implementation(libs.hilt.android)
    ksp(libs.hilt.android.compiler)
    implementation(libs.androidx.hilt.navigation.compose) // Hilt integration for Compose Navigation

    // Jetpack Compose BOM and UI Toolkit
    implementation(platform(libs.compose.bom))
    implementation(libs.compose.ui)
    implementation(libs.compose.ui.graphics)
    implementation(libs.compose.ui.tooling.preview)
    implementation(libs.material3)
    implementation(libs.androidx.activity.compose) // For Activity-level Compose setup

    // ViewModel and LiveData (Lifecycle)
    implementation(libs.androidx.lifecycle.viewmodel.compose) // For hiltViewModel()
    implementation(libs.androidx.lifecycle.runtime.compose) // For collectAsStateWithLifecycle

    // Navigation
    implementation(libs.androidx.navigation.compose)

    // Coroutines
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.coroutines.android)

    // AndroidX Core
    implementation(libs.androidx.core.ktx)

    // Testing
    // testImplementation(libs.junit)
    // androidTestImplementation(libs.androidx.test.ext)
    // androidTestImplementation(libs.androidx.test.espresso.core)
    // testImplementation(libs.kotlinx.coroutines.test)
    // testImplementation(libs.androidx.arch.core.testing) // For testing LiveData/ViewModels
    // TODO: Add specific test libraries for Compose UI testing if needed
    // androidTestImplementation(platform(libs.compose.bom))
    // androidTestImplementation(libs.compose.ui.test)
    // debugImplementation(libs.compose.ui.tooling)
    // debugImplementation(libs.compose.ui.test.manifest)
}
