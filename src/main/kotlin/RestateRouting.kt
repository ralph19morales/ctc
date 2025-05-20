package com.ralphmorales

import dev.restate.sdk.*
import dev.restate.sdk.http.vertx.RestateHttpServer
import dev.restate.sdk.kotlin.*
import dev.restate.sdk.kotlin.endpoint.endpoint
import domain.apis.*
import infra.services.TransactionService
import io.ktor.server.application.*
import io.ktor.server.application.Application
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.plugins.defaultheaders.*
import io.ktor.server.plugins.swagger.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Application.configureRestateRouting() {
        val createTransaction: CreateTransaction by inject()
        val chargeTransaction: ChargeTransaction by inject()
        val chargeTransactionFee: ChargeTransactionFee by inject()
        val getTransaction: GetTransaction by inject()
        val getApplicableFee: GetApplicableFee by inject()

        // expose the API using Ktor embedded server
        RestateHttpServer.listen(
                endpoint {
                        bind(
                                TransactionService(
                                        createTransaction,
                                        chargeTransaction,
                                        chargeTransactionFee,
                                        getTransaction,
                                        getApplicableFee
                                )
                        )
                }
        )
}
