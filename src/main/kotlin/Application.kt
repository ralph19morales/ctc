package com.ralphmorales

import io.ktor.server.application.*

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    configureAdministration()
    configureFrameworks()
    configureSerialization()
    configureHTTP()
    configureRouting()
}
