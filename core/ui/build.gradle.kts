plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    // alias(libs.plugins.hilt) // Not strictly needed if UI module is presentation only
    // alias(libs.plugins.ksp)    // For Hilt, if used
}

android {
    namespace = "com.cattailsw.retrl.core.ui" // **Ensure this is correct**
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
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true // This module is all about Compose UI
    }
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.composeCompiler.get()
    }
}

dependencies {
    // Project Dependencies
    // implementation(project(":core:domain")) // If domain models are directly used in common UI

    // Jetpack Compose BOM and UI Toolkit
    implementation(platform(libs.compose.bom))
    implementation(libs.compose.ui)
    implementation(libs.compose.ui.graphics)
    implementation(libs.compose.ui.tooling.preview)
    implementation(libs.material3) // Material Design 3 components
    implementation(libs.androidx.activity.compose) // For Activity-level Compose setup, if needed

    // Hilt (if providing ViewModels or using @AndroidEntryPoint in this module)
    // implementation(libs.hilt.android)
    // ksp(libs.hilt.android.compiler)

    // Other common UI libraries (e.g., Coil for image loading, Accompanist)
    // implementation(libs.coil.kt.compose)
    // implementation(libs.accompanist.permissions) // Example

    // AndroidX Core (useful for many things)
    implementation(libs.androidx.core.ktx)

    // Testing (optional, but good practice)
    // testImplementation(libs.junit)
    // androidTestImplementation(libs.androidx.test.ext)
    // androidTestImplementation(libs.androidx.test.espresso.core)
    // androidTestImplementation(platform(libs.compose.bom))
    // androidTestImplementation(libs.compose.ui.test)
    // debugImplementation(libs.compose.ui.tooling)
    // debugImplementation(libs.compose.ui.test.manifest)
}
