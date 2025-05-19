import com.ralphmorales.*
import domain.apis.CreateTransaction
import domain.models.Asset
import domain.models.AssetType
import domain.models.TransactionType
import infra.configurations.domainModules
import infra.persistence.utils.seedFeeTypes
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.server.testing.*
import java.math.BigDecimal
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.koin.core.context.startKoin

class RoutingTest :
        BehaviorSpec({
            // Initialize Koin for tests
            beforeSpec {
                startKoin { modules(domainModules) }
                // Seed test data
                val txCreate = org.koin.java.KoinJavaComponent.getKoin().get<CreateTransaction>()
                txCreate.execute(
                        amount = BigDecimal.valueOf(100),
                        asset = Asset.USD,
                        assetType = AssetType.FIAT,
                        type = TransactionType.TRANSFER
                )
                seedFeeTypes()
            }
            given("The /transaction/create route") {
                `when`("a valid CreateTransactionRequest is posted") {
                    then("it should return a successful response") {
                        testApplication {
                            application {
                                configureRouting()
                                configureSerialization()
                                configureHTTP()
                                configureErrorHandling()
                            }
                            val request =
                                    CreateTransactionRequest(
                                            amount = BigDecimal.valueOf(100),
                                            asset = Asset.USD,
                                            assetType = AssetType.FIAT,
                                            type = TransactionType.TRANSFER
                                    )
                            val response =
                                    client.post("/transaction/create") {
                                        contentType(ContentType.Application.Json)
                                        setBody(Json.encodeToString(request))
                                    }
                            response.status shouldBe HttpStatusCode.OK
                        }
                    }
                }
            }

            given("The /transaction/{transactionId}/charge route") {
                `when`("a valid transactionId is provided") {
                    then("it should return a successful response") {
                        testApplication {
                            application {
                                configureRouting()
                                configureSerialization()
                                configureHTTP()
                                configureErrorHandling()
                            }
                            val response = client.post("/transaction/1/charge")
                            response.status shouldBe HttpStatusCode.OK
                        }
                    }
                }
                `when`("transactionId is not found") {
                    then("it should return NotFound") {
                        testApplication {
                            application {
                                configureRouting()
                                configureSerialization()
                                configureHTTP()
                                configureErrorHandling()
                            }
                            val response = client.post("/transaction/999/charge")
                            response.status shouldBe HttpStatusCode.NotFound
                        }
                    }
                }
            }

            given("The /transaction/{transactionId}/charge-fees route") {
                `when`("a valid transactionId is provided") {
                    then("it should return a successful response") {
                        testApplication {
                            application {
                                configureRouting()
                                configureSerialization()
                                configureHTTP()
                                configureErrorHandling()
                            }
                            val response = client.post("/transaction/1/charge-fees")
                            response.status shouldBe HttpStatusCode.OK
                        }
                    }
                }
                `when`("transactionId is not found") {
                    then("it should return NotFound") {
                        testApplication {
                            application {
                                configureRouting()
                                configureSerialization()
                                configureHTTP()
                                configureErrorHandling()
                            }
                            val response = client.post("/transaction/999/charge-fees")
                            response.status shouldBe HttpStatusCode.NotFound
                        }
                    }
                }
            }

            given("The /transaction/{transactionId} route") {
                `when`("a valid transactionId is provided") {
                    then("it should return a successful response") {
                        testApplication {
                            application {
                                configureRouting()
                                configureSerialization()
                                configureHTTP()
                                configureErrorHandling()
                            }
                            val response = client.get("/transaction/1")
                            response.status shouldBe HttpStatusCode.OK
                        }
                    }
                }
                `when`("transactionId is not found") {
                    then("it should return NotFound") {
                        testApplication {
                            application {
                                configureRouting()
                                configureSerialization()
                                configureHTTP()
                                configureErrorHandling()
                            }
                            val response = client.post("/transaction/999")
                            response.status shouldBe HttpStatusCode.NotFound
                        }
                    }
                }
            }

            given("The /transaction/{transactionId}/applicable-fees route") {
                `when`("a valid transactionId is provided") {
                    then("it should return a successful response") {
                        testApplication {
                            application {
                                configureRouting()
                                configureSerialization()
                                configureHTTP()
                                configureErrorHandling()
                            }
                            val response = client.get("/transaction/1/applicable-fees")
                            response.status shouldBe HttpStatusCode.OK
                        }
                    }
                }
                `when`("transactionId is not found") {
                    then("it should return NotFound") {
                        testApplication {
                            application {
                                configureRouting()
                                configureSerialization()
                                configureHTTP()
                                configureErrorHandling()
                            }
                            val response = client.post("/transaction/999/applicable-fees")
                            response.status shouldBe HttpStatusCode.NotFound
                        }
                    }
                }
            }
        })
