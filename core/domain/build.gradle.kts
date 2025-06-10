plugins {
    alias(libs.plugins.kotlin.jvm)
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

dependencies {
    implementation(libs.kotlinx.coroutines.core)
    // Test dependencies (optional, but good practice)
    // testImplementation(libs.junit)
    // testImplementation(libs.kotlinx.coroutines.test)
}
