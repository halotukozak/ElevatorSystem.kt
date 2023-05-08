import org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpack

val ktorVersion = "2.0.2"
val kotlinWrappersVersion = "1.0.0-pre.547"

fun kotlinw(target: String) = "org.jetbrains.kotlin-wrappers:kotlin-$target"
fun ktor(target: String) = "io.ktor:ktor-$target:$ktorVersion"
fun ktorClient(target: String) = ktor("client-$target")
fun ktorServer(target: String) = ktor("server-$target")
fun kotlinx(target: String) = "org.jetbrains.kotlinx:kotlinx-$target"



plugins {
    kotlin("multiplatform") version "1.8.20"
    application
    kotlin("plugin.serialization") version "1.8.20"
}

group = "me.elevator"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/kotlinx-html/maven")
}

kotlin {
    jvm {
        jvmToolchain(17)
        withJava()
        testRuns["test"].executionTask.configure {
            useJUnitPlatform()
        }
    }
    js(IR) {
        binaries.executable()
        browser {
            testTask {
                enabled = false
            }
            commonWebpackConfig {
                cssSupport {
                    enabled.set(false)
                }
            }
        }
    }
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(kotlinx("serialization-json:1.5.0"))
                implementation(kotlinx("coroutines-core:1.7.0"))
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))

            }
        }
        val jvmMain by getting {
            dependencies {
                implementation(ktor("serialization"))
                implementation(ktorServer("content-negotiation"))
                implementation(ktor("serialization-kotlinx-json"))
                implementation(ktorServer("cors"))
                implementation(ktorServer("compression"))
                implementation(ktorServer("core-jvm"))
                implementation(ktorServer("netty"))

                implementation("ch.qos.logback:logback-classic:1.4.6")

            }
        }
        val jvmTest by getting
        val jsMain by getting {
            dependencies {
                implementation(ktorClient("js"))
                implementation(ktorClient("content-negotiation"))
                implementation(ktor("serialization-kotlinx-json"))

                implementation(project.dependencies.enforcedPlatform(kotlinw("wrappers-bom:$kotlinWrappersVersion")))
                implementation(kotlinw("react"))
                implementation(kotlinw("react-dom"))
                implementation(kotlinw("react-dom-legacy"))

                implementation(npm("core-js", "3.16.2"))
                implementation(kotlinw("ring-ui"))
                implementation(kotlinw("emotion"))
            }

        }
        val jsTest by getting
    }
}

application {
    mainClass.set("me.elevator.application.ServerKt")
}


tasks.getByName<JavaExec>("run") {
    dependsOn(tasks.named<Jar>("jvmJar"))
    classpath(tasks.named<Jar>("jvmJar"))
}

tasks.getByName<Jar>("jvmJar") {
    val taskName = if (project.hasProperty("isProduction")
        || project.gradle.startParameter.taskNames.contains("installDist")
    ) {
        "jsBrowserProductionWebpack"
    } else {
        "jsBrowserDevelopmentWebpack"
    }
    val webpackTask = tasks.getByName<KotlinWebpack>(taskName)
    dependsOn(webpackTask) // make sure JS gets compiled first
    from(File(webpackTask.destinationDirectory, webpackTask.outputFileName)) // bring output file along into the JAR
}

distributions {
    main {
        contents {
            from("$buildDir/libs") {
                rename("${rootProject.name}-jvm", rootProject.name)
                into("lib")
            }
        }
    }
}

tasks.register("stage") {
    dependsOn(tasks.getByName("installDist"))
    dependsOn("build")

    doLast {
//        delete(fileTree("build").exclude("build/install"))
    }
}