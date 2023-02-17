plugins {
    id("dev.nhalase.kotlin-library-conventions")
    kotlin("plugin.serialization")
}

dependencies {
    implementation(kotlin("reflect"))
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json")
    testImplementation("io.mockk:mockk")
}
