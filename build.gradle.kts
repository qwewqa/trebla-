plugins {
    kotlin("jvm") version "1.5.0-RC"
    id("com.github.johnrengelman.shadow") version "6.0.0"
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
    maven("https://dl.bintray.com/kotlin/kotlin-eap")
    maven("https://kotlin.bintray.com/kotlinx")
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core-jvm:1.4.2")
    implementation("com.github.ajalt.clikt:clikt:3.1.0")
    implementation("org.snakeyaml:snakeyaml-engine:2.1")
    implementation("com.fasterxml.jackson.jr:jackson-jr-objects:2.12.0")
    implementation("com.sparkjava:spark-kotlin:1.0.0-alpha")
    implementation("org.slf4j:slf4j-simple:1.7.25")
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
            jvmTarget = "11"
            apiVersion = "1.5"
            languageVersion = "1.5"
            freeCompilerArgs += "-Xopt-in=kotlin.RequiresOptIn"
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
