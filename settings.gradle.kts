pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "VintageTypewriterApp"
include(":app")
include(":core:data")
include(":core:domain")
include(":core:ui")
include(":core:sound")
include(":feature:editor")
include(":feature:export")
