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
include(":feature:editor")
include(":feature:export")
include(":core:domain")
include(":core:data")
include(":core:ui")
include(":core:sound")
