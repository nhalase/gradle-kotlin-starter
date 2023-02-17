plugins {
    id("dev.nhalase.kotlin-ktor-conventions")
    kotlin("plugin.serialization")
}

dependencies {
    implementation("io.github.microutils:kotlin-logging")
    implementation("io.ktor:ktor-server-core-jvm")
    implementation("io.ktor:ktor-server-netty-jvm")
    implementation("io.ktor:ktor-server-content-negotiation-jvm")
    implementation("io.ktor:ktor-server-call-logging-jvm")
    implementation("io.ktor:ktor-server-cors-jvm")
    implementation("io.ktor:ktor-server-auth-jvm")
    implementation("io.ktor:ktor-server-auth-jwt-jvm")
    implementation("io.ktor:ktor-server-resources-jvm")
    implementation("io.ktor:ktor-server-content-negotiation-jvm")
    implementation("io.ktor:ktor-serialization-kotlinx-json-jvm")
    implementation(project(":lib"))

    testImplementation("io.mockk:mockk")
    testImplementation("io.ktor:ktor-server-tests")
}

val mainClassStr by extra("io.ktor.server.netty.EngineMain")
application {
    mainClass.set(mainClassStr)

    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}
