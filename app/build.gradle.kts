plugins {
    id("dev.nhalase.kotlin-application-conventions")
    kotlin("plugin.serialization")
}

dependencies {
    implementation("io.github.microutils:kotlin-logging")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json")
    implementation(project(":lib"))

    testImplementation("io.mockk:mockk")
}

application {
    // Define the main class for the application.
    mainClass.set("dev.nhalase.MainKt")
}
