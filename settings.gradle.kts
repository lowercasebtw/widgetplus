pluginManagement {
    repositories {
        mavenCentral()
        gradlePluginPortal()
        maven("https://maven.fabricmc.net")
        maven("https://maven.kikugie.dev/snapshots")
        maven("https://repo.polyfrost.cc/releases")
    }
}

plugins {
    id("dev.kikugie.stonecutter") version "0.9.1"
}

stonecutter {
    kotlinController = true
    centralScript = "build.gradle.kts"
    create(rootProject) {
        fun mc(mcVersion: String, loaders: Iterable<String>) {
            for (loader in loaders) {
                version("$mcVersion-$loader", mcVersion)
            }
        }

        mc("26.1", listOf("fabric"))
        mc("26.2", listOf("fabric"))

        vcsVersion = "26.1-fabric"
    }
}

dependencyResolutionManagement {
    versionCatalogs {
        create("libs")
    }
}

rootProject.name = "WidgetPlus"
