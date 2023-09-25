buildscript {
    dependencies {
        classpath("com.squareup.sqldelight:gradle-plugin:1.5.5")
        classpath("com.arkivanov.decompose:decompose:2.1.0-compose-experimental-alpha-05")
        classpath("dev.icerock.moko:resources-generator:0.23.0")
//        classpath("com.codingfeline.buildkonfig:buildkonfig-gradle-plugin:0.13.3")
    }
}

plugins {
    kotlin("multiplatform").version("1.8.21").apply(false)
    id("com.android.application").version("8.1.0").apply(false)
    id("com.android.library").version("8.1.0").apply(false)
    kotlin("android").version("1.8.21").apply(false)
    id("org.jetbrains.compose").apply(false)
    kotlin("plugin.serialization") version "1.8.21"
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
