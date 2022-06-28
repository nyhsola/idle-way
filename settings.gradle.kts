include(
    "android",
    "asset",
    "core:common",
    "server:launcher"
)


pluginManagement {
    val kspDevtoolsVersion = "1.7.0-1.0.6"
    plugins {
        id("com.google.devtools.ksp") version kspDevtoolsVersion
    }
}

dependencyResolutionManagement {
    val kotlinVersion = "1.6.20"
    val ktxVersion = "1.10.0-b1"
    val gdxVersion = "1.11.0"
    val ashleyVersion = "1.7.4"
    val gradleTool = "7.0.4"
    val koinVersion = "3.2.0"
    val koinAnnotationVersion = "1.0.1"

    versionCatalogs {
        create("libs") {
            library("tgradle", "com.android.tools.build:gradle:$gradleTool")
            library("kgradle", "org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion")
            library("kstdlib", "org.jetbrains.kotlin:kotlin-stdlib:$kotlinVersion")

            library("kapp", "io.github.libktx:ktx-app:$ktxVersion")
            library("kmath", "io.github.libktx:ktx-math:$ktxVersion")

            library("koin", "io.insert-koin:koin-core:$koinVersion")
            library("koina", "io.insert-koin:koin-annotations:$koinAnnotationVersion")
            library("koinc", "io.insert-koin:koin-ksp-compiler:$koinAnnotationVersion")

            library("gashley", "com.badlogicgames.ashley:ashley:$ashleyVersion")
            library("gfreetype", "com.badlogicgames.gdx:gdx-freetype:$gdxVersion")
            library("gbackend", "com.badlogicgames.gdx:gdx-backend-headless:$gdxVersion")
            library("glwjgl3", "com.badlogicgames.gdx:gdx-backend-lwjgl3:$gdxVersion")

            library("gplatformdesktop", "com.badlogicgames.gdx:gdx-platform:$gdxVersion")
            library("gplatformfreetype", "com.badlogicgames.gdx:gdx-freetype-platform:$gdxVersion")
        }
    }
}