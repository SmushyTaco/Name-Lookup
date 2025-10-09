val archivesBaseName = providers.gradleProperty("archives_base_name")
rootProject.name = archivesBaseName.get()
pluginManagement {
    repositories {
        maven("https://maven.fabricmc.net") { name = "Fabric" }
        mavenCentral()
        gradlePluginPortal()
    }
    val loomVersion = providers.gradleProperty("loom_version")
    val kotlinVersion = providers.gradleProperty("kotlin_version")
    plugins {
        id("fabric-loom").version(loomVersion.get())
        kotlin("jvm").version(kotlinVersion.get())
        kotlin("plugin.serialization").version(kotlinVersion.get())
    }
}