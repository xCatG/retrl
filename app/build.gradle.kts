plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

dependencies {
    implementation(project(":feature:editor"))
    implementation(project(":feature:export"))
    implementation(project(":core:data")) // For Hilt graph
    implementation(project(":core:domain")) // For Hilt graph
    implementation(project(":core:ui")) // For Hilt graph
    implementation(project(":core:sound")) // For Hilt graph
}
