plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.hilt)
    alias(libs.plugins.ksp) // For Room and Hilt
}

android {
    namespace = "com.cattailsw.retrl.core.data" // **Ensure this is correct**
    compileSdk = 36 // Or your target SDK

    defaultConfig {
        minSdk = 24
        // targetSdk = 34 // Not typically set in library modules like this
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
    // Explicitly enable Compose if this module uses it (even for previews)
    // buildFeatures {
    //     compose = true
    // }
    // composeOptions {
    //     kotlinCompilerExtensionVersion = libs.versions.composeCompiler.get()
    // }
}

dependencies {
    // Project Dependencies
    implementation(project(":core:domain"))

    // Hilt for Dependency Injection
    implementation(libs.hilt.android)
    ksp(libs.hilt.android.compiler)

    // Room for Local Database
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx) // Kotlin extensions for Room (coroutines support)
    ksp(libs.androidx.room.compiler)

    // Kotlinx Serialization for JSON
    implementation(libs.kotlinx.serialization.json)

    // Coroutines
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.coroutines.android) // If Android-specific coroutine scopes are needed

    // AndroidX Core (for Context, etc., if needed directly)
    implementation(libs.androidx.core.ktx)

    // Testing (optional, but good practice)
    // testImplementation(libs.junit)
    // androidTestImplementation(libs.androidx.test.ext)
    // androidTestImplementation(libs.androidx.test.espresso.core)
    // testImplementation(libs.kotlinx.coroutines.test)
    // testImplementation(libs.androidx.room.testing) // For testing Room DB
}
