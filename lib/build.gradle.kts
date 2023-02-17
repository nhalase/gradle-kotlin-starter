plugins {
    id("dev.nhalase.kotlin-library-conventions")
    kotlin("plugin.serialization")
}

dependencies {
    implementation(kotlin("reflect"))
    api(project(":kotlin-ext"))
}
