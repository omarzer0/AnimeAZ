import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose")
    id("dev.icerock.mobile.multiplatform-resources")
}

kotlin {
    jvm()
    sourceSets {
        val jvmMain by getting  {
            dependencies {
                implementation(compose.desktop.currentOs)
                implementation(project(":shared"))

                // Decompose + Router
                implementation("io.github.xxfast:decompose-router:0.3.0")
                implementation("com.arkivanov.decompose:decompose:2.1.0-compose-experimental-alpha-05")
                implementation("com.arkivanov.decompose:extensions-compose-jetbrains:2.1.0-compose-experimental-alpha-05")
                implementation("com.arkivanov.essenty:parcelable:1.1.0")

                // Koin
                implementation("io.insert-koin:koin-core:3.4.2")

//                // MOKO resources
//                implementation("dev.icerock.moko:resources-compose:0.23.0")
            }
        }
    }
}

compose.desktop {
    application {
        mainClass = "MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "az.zero.animeaz"
            packageVersion = "1.0.0"
        }
    }
}

val nameSpace = "az.zero.animeaz"

multiplatformResources {
    multiplatformResourcesPackage = nameSpace
    multiplatformResourcesClassName = "SharedRes"
    disableStaticFrameworkWarning = true
}
