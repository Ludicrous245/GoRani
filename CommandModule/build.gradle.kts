
plugins {
    application
    kotlin("jvm") version "1.5.10"
    id("idea")

}

group = "xyz.ludicrous245"
version = "1.0"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))

    implementation("dev.kord:kord-core:0.8.0-M14")

    api(fileTree("src/main/libs") { include("*.jar") })
}
