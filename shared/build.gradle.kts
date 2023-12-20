import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    alias(libs.plugins.multiplatform)
    alias(libs.plugins.compose)
    alias(libs.plugins.kotlinx.serialization)
    alias(libs.plugins.android.application)
    alias(libs.plugins.sqlDelight)
    alias(libs.plugins.moko.resources)
    id("kotlin-parcelize")
}


@OptIn(org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi::class)
kotlin {
    targetHierarchy.default()
    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = "17"
            }
        }
    }

    jvm("desktop")

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "shared"
            export(libs.moko.resources)
        }
    }

    sourceSets {
        all {
            languageSettings {
                optIn("org.jetbrains.compose.resources.ExperimentalResourceApi")
            }
        }
        val commonMain by getting {
            dependencies {
                implementation(compose.runtime)
                implementation(compose.material3)
                implementation(compose.materialIconsExtended)
                @OptIn(org.jetbrains.compose.ExperimentalComposeLibrary::class)
                implementation(compose.components.resources)

                // SqlDelight
                implementation(libs.sqlDelight.runtime)
                implementation(libs.sqlDelight.extentions.coroutines)

                // Coroutines
                implementation(libs.kotlinx.coroutines.core)

                // Ktor
                implementation(libs.ktor.core)
                implementation(libs.ktor.contentNegotiation)
                implementation(libs.ktor.jsonSerialization)
                implementation(libs.ktor.logging)

                // Koin
                implementation(libs.koin.core)

                // Image Loading
                implementation(libs.composeImageLoader)

                // Decompose + Router
                implementation(libs.decompose.jetbrains)
                implementation(libs.decompose)
                implementation(libs.router)
                implementation(libs.essenty)

                // kotlinx datetime
                implementation(libs.kotlinx.datetime)

                // KMP Settings
                implementation(libs.multiplatformSettings)

                // Pull to refresh
                implementation(libs.pullToRefresh)

                // Feather Icons
                implementation(libs.composeIcons.featherIcons)

                implementation(project(":paging"))

            }
        }

        val androidMain by getting {
            dependsOn(commonMain)
            dependencies {
                implementation(libs.androidx.appcompat)
                implementation(libs.androidx.activityCompose)
                implementation(libs.koin.android)

                implementation(libs.ktor.client.android)
                implementation(libs.sqlDelight.driver.android)
            }
        }

        val desktopMain by getting {
            dependsOn(commonMain)
            dependencies {
                implementation(compose.desktop.common)
                implementation(compose.desktop.currentOs)
                implementation(libs.ktor.client.cio)
                implementation(libs.sqlDelight.driver.jvm)
            }
        }

        val iosMain by getting {
            dependsOn(commonMain)
            dependencies {
//                implementation(compose.desktop.common)

                implementation(libs.ktor.client.darwin)
                implementation(libs.sqlDelight.driver.native)
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


    }
}

val nameSpace = "az.zero.animeaz"

dependencies{
    commonMainApi(libs.moko.mvvm)
    commonMainApi(libs.moko.flow)
    commonMainApi(libs.moko.resources)
}

android {
    namespace = nameSpace
    compileSdk = 34

    defaultConfig {
        minSdk = 24
        targetSdk = 34

        applicationId = "$nameSpace.androidApp"
        versionCode = 1
        versionName = "1.0.0"
    }
    sourceSets["main"].apply {
        manifest.srcFile("src/androidMain/AndroidManifest.xml")
        res.srcDirs("src/androidMain/resources")
        resources.srcDirs("src/commonMain/resources")
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

compose.desktop {
    application {
        mainClass = "MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "nameSpace.desktopApp"
            packageVersion = "1.0.0"
        }
    }
}

sqldelight {
    databases {
        create("AppDatabase") {
            packageName.set("$nameSpace.database")
        }
    }
}

multiplatformResources {
    multiplatformResourcesPackage = nameSpace
    multiplatformResourcesClassName = "SharedRes"
    disableStaticFrameworkWarning = true
}
