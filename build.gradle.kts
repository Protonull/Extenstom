plugins {
    id("java")
    // https://github.com/GradleUp/shadow/releases
    id("com.gradleup.shadow") version("8.3.6")
}

val MICROTUS_VERSION = "1.5.1"
val EXTENSTOM_VERSION = "1"
val MAIN_CLASS = "uk.protonull.extenstom.Extenstom"

group = "uk.protonull.extenstom"
version = "${MICROTUS_VERSION}-${EXTENSTOM_VERSION}"

dependencies {
    // https://github.com/OneLiteFeatherNET/Microtus/releases
    implementation(platform("net.onelitefeather.microtus:bom:${MICROTUS_VERSION}"))
    implementation("net.onelitefeather.microtus:Microtus")
}

repositories {
    mavenCentral()
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
}

tasks {
    compileJava {
        options.encoding = "UTF-8"
        options.release = 21
    }
    jar {
        manifest {
            attributes["Main-Class"] = MAIN_CLASS
        }
    }
    shadowJar {
        archiveClassifier = ""
        mergeServiceFiles()
    }
    val cleanDistDir = register<Delete>("cleanDistDir") {
        delete(fileTree(file("dist/")) {
            include("*.jar")
        })
    }
    val copyDistJar = register<Copy>("copyDistJar") {
        from(shadowJar, jar)
        dependsOn(cleanDistDir)
        into(file("dist/"))
    }
    clean {
        dependsOn(cleanDistDir)
    }
    build {
        dependsOn(shadowJar, copyDistJar)
    }
    register<JavaExec>("run") {
        mainClass = MAIN_CLASS
        classpath = sourceSets["main"].runtimeClasspath
        jvmArgs = listOf(
            "-Dextenstom.host=0.0.0.0",
            "-Dextenstom.port=25565",
            "-Dminestom.bstats.id=00000000-0000-0000-0000-000000000000",
            "-Dminestom.terminal.disabled=true",
        )
    }
}
