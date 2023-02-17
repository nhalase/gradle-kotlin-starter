package dev.nhalase

import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.plugins.callloging.CallLogging
import io.ktor.server.request.path

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // application.conf references the main function.
fun Application.module() {
    install(CallLogging) {
        filter {
            !it.request.path().contains("/healthz") && !it.request.path().contains("/readyz")
        }
    }
}
