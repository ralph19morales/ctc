package com.ralphmorales

import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.application.call
import io.ktor.server.application.install
import io.ktor.server.plugins.statuspages.StatusPages
import io.ktor.server.plugins.statuspages.exception
import io.ktor.server.response.respond
import jakarta.persistence.NoResultException
import kotlinx.serialization.Serializable

fun Application.configureErrorHandling() {
    install(StatusPages) {
        exception<NoResultException> { call, cause ->
            call.respond(
                    HttpStatusCode.NotFound,
                    ErrorResponse("not found", cause.localizedMessage)
            )
        }
        exception<IllegalArgumentException> { call, cause ->
            call.respond(
                    HttpStatusCode.BadRequest,
                    ErrorResponse("bad request", cause.localizedMessage)
            )
        }
        exception<Throwable> { call, cause ->
            call.respond(
                    HttpStatusCode.InternalServerError,
                    ErrorResponse("error", cause.localizedMessage)
            )
        }
    }
}

@Serializable data class ErrorResponse(val status: String, val message: String)
