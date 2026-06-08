pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
    plugins {
        // 🚨 Убедитесь, что эти версии совместимы!
        id("com.android.application") version "9.2.1" apply false // Пример AGP v8+
        id("org.jetbrains.kotlin.android") version "2.4.0" apply false // Пример Kotlin v1.9
        id("com.google.dagger.hilt.android") version "2.59.2" apply false // Ваш Hilt Plugin
    }
}
plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "ComposeOSM"
include(":app")
