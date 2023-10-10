import org.jetbrains.compose.desktop.application.tasks.AbstractNativeMacApplicationPackageAppDirTask
import org.jetbrains.kotlin.gradle.plugin.mpp.AbstractExecutable
import org.jetbrains.kotlin.gradle.plugin.mpp.NativeBinary
import org.jetbrains.kotlin.gradle.tasks.KotlinNativeLink
import org.jetbrains.kotlin.library.impl.KotlinLibraryLayoutImpl
import java.io.File
import java.io.FileFilter
import org.jetbrains.kotlin.konan.file.File as KonanFile

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


//    targets.withType(org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget::class.java).all {
//        binaries.withType(org.jetbrains.kotlin.gradle.plugin.mpp.Framework::class.java).all {
//            // export correct artifact to use all classes of library directly from Swift
//            export("dev.icerock.moko:mvvm-core:0.16.1")
//        }
//    }

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "shared"
//            export("dev.icerock.moko:resources:0.23.0")
//            export("dev.icerock.moko:graphics:0.9.0")
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

        val iosMain by getting {
            dependsOn(commonMain)

            dependencies {
                implementation("io.ktor:ktor-client-darwin:$ktorVersion")
                implementation("com.squareup.sqldelight:native-driver:$sqlDelightVersion")
            }
        }

    }
}

dependencies {
//    implementation("androidx.core:core:1.12.0")
//    implementation("androidx.annotation:annotation-jvm:1.7.0")
    commonMainApi("dev.icerock.moko:mvvm-compose:0.16.1")
    commonMainApi("dev.icerock.moko:mvvm-flow-compose:0.16.1")
    commonMainApi("dev.icerock.moko:resources-compose:0.23.0")
    commonMainApi("dev.icerock.moko:biometry-compose:0.4.0")
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

tasks.withType<KotlinNativeLink>()
    .matching { linkTask -> linkTask.binary is AbstractExecutable }
    .configureEach {
        val task: KotlinNativeLink = this

        doLast {
            val binary: NativeBinary = task.binary
            val outputDir: File = task.outputFile.get().parentFile
            task.libraries
                .filter { library -> library.extension == "klib" }
                .filter(File::exists)
                .forEach { inputFile ->
                    val klibKonan = KonanFile(inputFile.path)
                    val klib = KotlinLibraryLayoutImpl(
                        klib = klibKonan,
                        component = "default"
                    )
                    val layout = klib.extractingToTemp

                    // extracting bundles
                    layout
                        .resourcesDir
                        .absolutePath
                        .let(::File)
                        .listFiles(FileFilter { it.extension == "bundle" })
                        // copying bundles to app
                        ?.forEach { bundleFile ->
                            logger.info("${bundleFile.absolutePath} copying to $outputDir")
                            bundleFile.copyRecursively(
                                target = File(outputDir, bundleFile.name),
                                overwrite = true
                            )
                        }
                }
        }
    }

tasks.withType<AbstractNativeMacApplicationPackageAppDirTask> {
    val task: AbstractNativeMacApplicationPackageAppDirTask = this

    doLast {
        val execFile: File = task.executable.get().asFile
        val execDir: File = execFile.parentFile
        val destDir: File = task.destinationDir.asFile.get()
        val bundleID: String = task.bundleID.get()

        val outputDir = File(destDir, "$bundleID.app/Contents/Resources")
        outputDir.mkdirs()

        execDir.listFiles().orEmpty()
            .filter { it.extension == "bundle" }
            .forEach { bundleFile ->
                logger.info("${bundleFile.absolutePath} copying to $outputDir")
                bundleFile.copyRecursively(
                    target = File(outputDir, bundleFile.name),
                    overwrite = true
                )
            }
    }
}