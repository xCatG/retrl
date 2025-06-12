plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.hilt)
    alias(libs.plugins.ksp) // For Hilt
}

android {
    namespace = "com.cattailsw.retrl.core.sound" // **Ensure this is correct**
    compileSdk = 36 // Or your target SDK

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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    // No Compose needed for this module based on current plan
    // buildFeatures {
    //     compose = true
    // }
    // composeOptions {
    //     kotlinCompilerExtensionVersion = libs.versions.composeCompiler.get()
    // }
}

dependencies {
    // Hilt for Dependency Injection
    implementation(libs.hilt.android)
    ksp(libs.hilt.android.compiler)

    // AndroidX Core (for Context, etc.)
    implementation(libs.androidx.core.ktx)

    // Coroutines (if SoundManager uses them, though not in current snippet)
    // implementation(libs.kotlinx.coroutines.core)
    // implementation(libs.kotlinx.coroutines.android)


    // Testing (optional, but good practice)
    // testImplementation(libs.junit)
    // androidTestImplementation(libs.androidx.test.ext)
    // androidTestImplementation(libs.androidx.test.espresso.core)
}
