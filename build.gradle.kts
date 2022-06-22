import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    application
    kotlin("jvm") version "1.5.10"
    id("com.github.johnrengelman.shadow") version "7.1.2"
    id("idea")

}

group = "com.ludicrous245"
version = "1.0"

repositories {
    mavenCentral()

    maven("https://oss.sonatype.org/content/repositories/snapshots")
    maven("https://m2.dv8tion.net/releases")
    maven("https://jitpack.io")
}

dependencies {
    implementation(kotlin("stdlib"))
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.2")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")

    implementation("dev.kord:kord-core:0.8.0-M14")
    implementation("dev.kord:kord-voice:0.8.0-M14")
    implementation("com.github.kimcore:inko.kt:1.1")
    implementation("im.kimcore:Josa.kt:1.6")
    implementation("dev.schlaubi.lavakord:kord-jvm:3.6.2")
    implementation("com.google.http-client:google-http-client-jackson2:1.41.8")
    implementation("com.google.apis:google-api-services-youtube:v3-rev20220418-1.32.1")

    api(fileTree("src/main/libs") { include("*.jar") })
}

application{
    mainClass.set("com.ludicrous245.GoRaniKt")
}

tasks {
    named<ShadowJar>("shadowJar") {
        archiveBaseName.set("Gorani")
        mergeServiceFiles()
        manifest {
            attributes(mapOf("Main-Class" to "com.ludicrous245.GoRaniKt"))
        }
    }
}

tasks {
    build {
        dependsOn(shadowJar)
    }
}