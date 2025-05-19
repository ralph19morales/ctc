package com.ralphmorales

import com.fasterxml.jackson.databind.*
import domain.apis.*
import domain.models.Asset
import domain.models.AssetType
import domain.models.TransactionType
import io.github.flaxoos.ktor.server.plugins.ratelimiter.*
import io.github.flaxoos.ktor.server.plugins.ratelimiter.implementations.*
import io.ktor.serialization.jackson.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.plugins.defaultheaders.*
import io.ktor.server.plugins.swagger.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.math.BigDecimal
import org.koin.ktor.ext.inject

fun Application.configureRouting() {
        val createTransaction: CreateTransaction by inject()
        val chargeTransaction: ChargeTransaction by inject()
        val chargeTransactionFee: ChargeTransactionFee by inject()
        val getTransaction: GetTransaction by inject()
        val getApplicableFee: GetApplicableFee by inject()

        routing {
                post("/transaction/create") {
                        val request = call.receive<CreateTransactionRequest>()
                        call.respond(
                                createTransaction.execute(
                                        request.amount,
                                        request.asset,
                                        request.assetType,
                                        request.type
                                )
                        )
                }
                post("/transaction/{transactionId}/charge") {
                        val transactionId =
                                call.parameters["transactionId"]
                                        ?: return@post call.respondText(
                                                "Missing or malformed transactionId",
                                                status = io.ktor.http.HttpStatusCode.BadRequest
                                        )
                        call.respond(chargeTransaction.execute(transactionId.toLong()))
                }

                post("/transaction/{transactionId}/charge-fees") {
                        val transactionId =
                                call.parameters["transactionId"]
                                        ?: return@post call.respondText(
                                                "Missing or malformed transactionId",
                                                status = io.ktor.http.HttpStatusCode.BadRequest
                                        )
                        call.respond(chargeTransactionFee.execute(transactionId.toLong()))
                }

                get("/transaction/{transactionId}") {
                        val transactionId =
                                call.parameters["transactionId"]
                                        ?: return@get call.respondText(
                                                "Missing or malformed transactionId",
                                                status = io.ktor.http.HttpStatusCode.BadRequest
                                        )
                        call.respond(getTransaction.execute(transactionId.toLong()))
                }

                get("/transaction/{transactionId}/applicable-fees") {
                        val transactionId =
                                call.parameters["transactionId"]
                                        ?: return@get call.respondText(
                                                "Missing or malformed transactionId",
                                                status = io.ktor.http.HttpStatusCode.BadRequest
                                        )
                        call.respond(getApplicableFee.execute(transactionId.toLong()))
                }
        }
}

data class CreateTransactionRequest(
        val amount: BigDecimal,
        val asset: Asset,
        val assetType: AssetType,
        val type: TransactionType
)
