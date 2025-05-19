package com.ralphmorales

import infra.persistence.utils.seedFeeTypes
import io.ktor.server.application.*
import org.slf4j.LoggerFactory

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    configureAdministration()
    configureFrameworks()
    configureSerialization()
    configureHTTP()
    configureRouting()
    configureErrorHandling()

    monitor.subscribe(ApplicationStarted) { seedFeeTypes() }
}

val logger = LoggerFactory.getLogger("Infra")
