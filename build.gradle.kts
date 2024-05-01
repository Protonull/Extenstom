// Update Gradle Wrapper using: ./gradlew wrapper --distribution-type bin --gradle-version <version>
// See Gradle's releases here: https://gradle.org/releases/

plugins {
    id("java")
    // ShadowJar (https://github.com/johnrengelman/shadow/releases)
    id("com.github.johnrengelman.shadow") version "8.1.1"
    // Git Patcher (https://github.com/zml2008/gitpatcher)
    id("ca.stellardrift.gitpatcher") version "1.1.0"
}

group = "uk.protonull.minestom"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://jitpack.io") // com.github.MadMartian:hydrazine-path-finding
}

dependencies {
    implementation("net.minestom:minestom:dev")
    implementation("ch.qos.logback:logback-classic:1.5.6")
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}

gitPatcher.patchedRepos {
    create("Minestom") {
        submodule = "libs/minestom/upstream"
        target.set(File("libs/minestom/patched"))
        patches.set(File("libs/minestom/patches"))
    }
}

tasks {
    jar {
        manifest {
            attributes["Main-Class"] = "uk.protonull.minestom.BasicMinestomServer"
        }
    }
    build {
        dependsOn(shadowJar)
    }
    shadowJar {
        mergeServiceFiles()
        archiveClassifier.set("") // Prevent the -all suffix
    }
}
