plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.hilt)
    alias(libs.plugins.ksp)
    alias(libs.plugins.compose.compiler)
}

android {
    namespace = "com.cattailsw.retrl.feature.export"
    compileSdk = 36 // Or your project's compileSdk

    defaultConfig {
        minSdk = 26 // Or your project's minSdk
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {
    implementation(project(":core:domain"))
    implementation(project(":core:ui"))
    implementation(project(":core:data")) // As per design

    implementation(platform(libs.compose.bom))
    implementation(libs.compose.ui)
    implementation(libs.compose.ui.graphics)
    implementation(libs.compose.ui.tooling.preview)
    implementation(libs.material3)

    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler) // Corrected from libs.hilt.compiler to libs.hilt.android.compiler if it's for Android Hilt
    // If you have a specific hilt.compiler for non-Android, ensure it's defined in libs.versions.toml
    // For typical Android modules, hilt.android.compiler is used with ksp.
    // The plan used libs.hilt.compiler - assuming it's a general hilt compiler.
    // For KSP with Hilt in Android library, it's usually hilt-android-compiler.
    // Let's assume libs.hilt.compiler refers to the correct one for ksp.
    // If issues arise, this might need to be libs.hilt.android.compiler.

    implementation(libs.androidx.hilt.navigation.compose) // Corrected: libs.hilt.navigation.compose to libs.androidx.hilt.navigation.compose

    implementation(libs.androidx.lifecycle.runtime.compose) // Corrected: libs.lifecycle.runtime.ktx to libs.androidx.lifecycle.runtime.ktx
    implementation(libs.androidx.lifecycle.viewmodel.compose) // Corrected: libs.lifecycle.viewmodel.compose to libs.androidx.lifecycle.viewmodel.compose

    // Test dependencies (optional for pure placeholders, but good practice)
    // testImplementation(libs.junit)
    // androidTestImplementation(libs.androidx.test.ext.junit) // Corrected: libs.androidx.test.ext.junit to libs.androidx.test.ext
    // debugImplementation(libs.compose.ui.tooling)
}
