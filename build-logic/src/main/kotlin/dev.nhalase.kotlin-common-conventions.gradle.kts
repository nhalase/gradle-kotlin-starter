import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    // Apply the org.jetbrains.kotlin.jvm Plugin to add support for Kotlin.
    kotlin("jvm")
}

java {
    withSourcesJar()
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}

tasks.withType(org.jetbrains.kotlin.gradle.tasks.KotlinJvmCompile::class.java) {
    compilerOptions {
        jvmTarget.set(JvmTarget.JVM_17)
        freeCompilerArgs.set(listOf("-Xjsr305=strict", "-opt-in=kotlin.RequiresOptIn"))
    }
}

tasks.withType(Test::class.java) {
    useJUnitPlatform()
    jvmArgs(
        "--add-opens", "java.base/java.time=ALL-UNNAMED",
        "--add-opens", "java.base/java.lang.reflect=ALL-UNNAMED"
    )
    testLogging {
        showStandardStreams = true
        events = setOf(
            org.gradle.api.tasks.testing.logging.TestLogEvent.FAILED,
            org.gradle.api.tasks.testing.logging.TestLogEvent.PASSED,
            org.gradle.api.tasks.testing.logging.TestLogEvent.SKIPPED,
            org.gradle.api.tasks.testing.logging.TestLogEvent.STANDARD_OUT,
            org.gradle.api.tasks.testing.logging.TestLogEvent.STANDARD_ERROR
        )
    }
}

dependencies {
    val ktorVersion = "2.2.3"
    implementation(platform("io.ktor:ktor-bom:$ktorVersion"))
    constraints {
        // Define dependency versions as constraints
        implementation("org.slf4j:slf4j-api:2.0.6")
        implementation("io.github.microutils:kotlin-logging:3.0.5")
        implementation("io.mockk:mockk:1.13.2")
        implementation("ch.qos.logback:logback-classic:1.4.5")
        implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.4.1")
    }
}

testing {
    suites {
        // Configure the built-in test suite
        val test by getting(JvmTestSuite::class) {
            // Use JUnit Jupiter test framework
            useJUnitJupiter("5.9.1")
        }
    }
}
