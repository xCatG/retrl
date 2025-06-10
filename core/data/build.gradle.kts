plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt") // For Room annotationProcessor
}

dependencies {
    api(project(":core:domain")) // api because domain models might be exposed
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
    implementation("javax.inject:javax.inject:1")

    // Room
    def room_version = "2.6.1" // Use a recent version
    implementation("androidx.room:room-runtime:$room_version")
    kapt("androidx.room:room-compiler:$room_version") // Changed from annotationProcessor to kapt
    implementation("androidx.room:room-ktx:$room_version") // For Flow and suspend support

    // Gson (for TypeConverter)
    implementation("com.google.code.gson:gson:2.10.1") // Use a recent version
}
