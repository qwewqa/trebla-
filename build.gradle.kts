import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.4.0"
    id("com.github.johnrengelman.shadow") version "6.0.0"
    id("org.beryx.runtime") version "1.11.1"
    antlr
    application
}

group = "xyz.qwewqa.trebla"
version = "0.2.0"

application {
    applicationName = "trebla"
    mainClassName = "xyz.qwewqa.trebla.MainKt"
}

repositories {
    mavenCentral()
    maven ("https://dl.bintray.com/kotlin/kotlin-eap")
    maven ("https://kotlin.bintray.com/kotlinx")
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core-jvm:1.3.9")
    implementation("com.github.ajalt:clikt:2.7.1")
    implementation("org.snakeyaml:snakeyaml-engine:2.1")
    implementation("com.fasterxml.jackson.jr:jackson-jr-objects:2.12.0")
    antlr("org.antlr:antlr4:4.8")
}

tasks {
    generateGrammarSource {
        maxHeapSize = "64m"
        arguments = arguments + listOf("-visitor", "-lib", "src/main/java/xyz/qwewqa/trebla/grammar/generated")
        outputDirectory = File("src/main/java/xyz/qwewqa/trebla/grammar/generated")
    }

    compileKotlin {
        kotlinOptions {
            jvmTarget = "1.8"
            freeCompilerArgs += "-Xopt-in=kotlin.RequiresOptIn"
            freeCompilerArgs += "-XXLanguage:+NewInference"
            apiVersion = "1.4"
            languageVersion = "1.4"
        }
        dependsOn += generateGrammarSource
        dependsOn += "stdZip"
    }

    register<Zip>("stdZip") {
        from("std")
        include("**/*")
        archiveFileName.set("std.zip")
        destinationDirectory.set(file("src/main/resources"))
    }
}

tasks.withType<KotlinCompile>().configureEach {
    kotlinOptions {
        useIR = true
    }
}
