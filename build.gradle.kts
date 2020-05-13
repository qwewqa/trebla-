plugins {
    kotlin("jvm") version "1.3.72"
    id("com.github.johnrengelman.shadow") version "6.0.0"
    id("org.beryx.runtime") version "1.11.1"
    antlr
    application
}

group = "xyz.qwewqa.sono"
version = "0.1.0"

application {
    applicationName = "sono"
    mainClassName = "xyz.qwewqa.sono.MainKt"
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.7")
    implementation("com.github.ajalt:clikt:2.7.1")
    implementation("org.snakeyaml:snakeyaml-engine:2.1")
    implementation("com.google.code.gson:gson:2.8.6")
    antlr("org.antlr:antlr4:4.8")
}

tasks {
    generateGrammarSource {
        maxHeapSize = "64m"
        arguments = arguments + listOf("-visitor", "-lib", "src/main/java/xyz/qwewqa/sono/grammar/generated")
        outputDirectory = File("src/main/java/xyz/qwewqa/sono/grammar/generated")
    }

    compileKotlin {
        kotlinOptions {
            jvmTarget = "1.8"
            freeCompilerArgs += "-Xopt-in=kotlin.RequiresOptIn"
            freeCompilerArgs += "-XXLanguage:+NewInference"
            languageVersion = "1.4"
        }
        dependsOn += generateGrammarSource
    }
}

runtime {
    targetPlatform("win") {
        setJdkHome(jdkDownload("https://github.com/AdoptOpenJDK/openjdk14-binaries/releases/download/jdk-14.0.2%2B12/OpenJDK14U-jdk_x64_windows_hotspot_14.0.2_12.zip"))
    }
    targetPlatform("mac") {
        setJdkHome(jdkDownload("https://github.com/AdoptOpenJDK/openjdk14-binaries/releases/download/jdk-14.0.2%2B12/OpenJDK14U-jdk_x64_mac_hotspot_14.0.2_12.tar.gz"))
    }
    targetPlatform("linux") {
        setJdkHome(jdkDownload("https://github.com/AdoptOpenJDK/openjdk14-binaries/releases/download/jdk-14.0.2%2B12/OpenJDK14U-jdk_x64_linux_hotspot_14.0.2_12.tar.gz"))
    }
}