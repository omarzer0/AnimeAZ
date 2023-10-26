plugins {
    kotlin("multiplatform")
    id("com.android.library")
    id("org.jetbrains.compose")
    id("kotlinx-serialization")
    id("kotlin-parcelize")

    id("com.squareup.sqldelight")
    id("dev.icerock.mobile.multiplatform-resources")
}

val nameSpace = "az.zero.animeaz"

android {
    namespace = nameSpace
    compileSdk = 34
    defaultConfig {
        minSdk = 25
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

val coroutinesVersion = "1.7.3"
val ktorVersion = "2.3.3"
val sqlDelightVersion = "1.5.5"
val dateTimeVersion = "0.4.0"
val serializationVersion = "2.3.2"
val koinVersion = "3.4.2"

@OptIn(org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi::class)
kotlin {
    targetHierarchy.default()

    jvm("desktop")

    android {
        compilations.all {
            kotlinOptions {
                jvmTarget = "17"
                allWarningsAsErrors = false
                // To ignore ExperimentalMaterial3Api error
                freeCompilerArgs += listOf(
                    "-opt-in=androidx.compose.material3.ExperimentalMaterial3Api"
                )
            }
        }
    }

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "shared"
            export("dev.icerock.moko:resources:0.23.0")
            export("dev.icerock.moko:graphics:0.9.0")
        }
    }


    sourceSets {
        val commonMain by getting {
            dependencies {

                implementation(compose.runtime)
                implementation(compose.foundation)
                implementation(compose.material3)
                implementation(compose.materialIconsExtended)
                @OptIn(org.jetbrains.compose.ExperimentalComposeLibrary::class)
                implementation(compose.components.resources)

                // SqlDelight
                implementation("com.squareup.sqldelight:runtime:$sqlDelightVersion")
                implementation("com.squareup.sqldelight:coroutines-extensions:$sqlDelightVersion")

                // Coroutines
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")

                // Ktor
                implementation("io.ktor:ktor-client-core:$ktorVersion")
                implementation("io.ktor:ktor-client-content-negotiation:$ktorVersion")
                implementation("io.ktor:ktor-serialization-kotlinx-json:$serializationVersion")
                implementation("io.ktor:ktor-client-logging:$ktorVersion")

                // Koin
                implementation("io.insert-koin:koin-core:${koinVersion}")

                // Image Loading
                api("io.github.qdsfdhvh:image-loader:1.6.4")

                // Decompose + Router
                implementation("io.github.xxfast:decompose-router:0.3.0")
                implementation("com.arkivanov.decompose:decompose:2.1.0-compose-experimental-alpha-05")
                implementation("com.arkivanov.decompose:extensions-compose-jetbrains:2.1.0-compose-experimental-alpha-05")
                implementation("com.arkivanov.essenty:parcelable:1.1.0")

                // Kotlin date-time
                implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.4.1")

                // My Paging library
                implementation(project(":paging"))

                // KMP preferences
                implementation("com.russhwolf:multiplatform-settings-no-arg:1.0.0")

                // Swipe to refresh
                implementation("dev.materii.pullrefresh:pullrefresh:1.0.1")

            }
        }

        val androidMain by getting {
            dependencies {
                implementation("io.ktor:ktor-client-android:$ktorVersion")
                implementation("com.squareup.sqldelight:android-driver:$sqlDelightVersion")
                implementation("androidx.appcompat:appcompat:1.6.1")
                implementation("androidx.activity:activity-compose:1.7.2")
            }
        }

        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }

        val androidUnitTest by getting
        val iosX64Main by getting
        val iosArm64Main by getting
        val iosSimulatorArm64Main by getting

        val iosX64Test by getting
        val iosArm64Test by getting
        val iosSimulatorArm64Test by getting
        val iosTest by getting {
            dependsOn(commonTest)
            iosX64Test.dependsOn(this)
            iosArm64Test.dependsOn(this)
            iosSimulatorArm64Test.dependsOn(this)
        }

        val iosMain by getting {
            dependsOn(commonMain)
            iosX64Main.dependsOn(this)
            iosArm64Main.dependsOn(this)
            iosSimulatorArm64Main.dependsOn(this)
            dependencies {
                implementation("io.ktor:ktor-client-darwin:$ktorVersion")
                implementation("com.squareup.sqldelight:native-driver:$sqlDelightVersion")
            }
        }

        val desktopMain by getting {
            dependsOn(commonMain)
            dependencies {
                implementation(compose.desktop.common)
                implementation("io.ktor:ktor-client-cio:2.3.1")
                implementation("com.squareup.sqldelight:sqlite-driver:1.5.4")
            }
        }

    }
}

dependencies {
    commonMainApi("dev.icerock.moko:mvvm-compose:0.16.1")
    commonMainApi("dev.icerock.moko:mvvm-flow-compose:0.16.1")
    commonMainApi("dev.icerock.moko:resources-compose:0.23.0")
//    commonMainApi("dev.icerock.moko:biometry-compose:0.4.0")
}

sqldelight {
    database("AppDatabase") {
        packageName = "$nameSpace.database"
    }
}

multiplatformResources {
    multiplatformResourcesPackage = nameSpace
    multiplatformResourcesClassName = "SharedRes"
    disableStaticFrameworkWarning = true
}