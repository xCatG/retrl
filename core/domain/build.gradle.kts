plugins {
    alias(libs.plugins.kotlin.jvm)
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}
kotlin {
    jvmToolchain(17)
}

dependencies {
    implementation(libs.kotlinx.coroutines.core)
    // Test dependencies (optional, but good practice)
    // testImplementation(libs.junit)
    // testImplementation(libs.kotlinx.coroutines.test)
}
