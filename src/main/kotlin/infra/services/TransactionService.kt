package infra.services

import com.ralphmorales.CreateTransactionRequest
import dev.restate.sdk.annotation.Handler
import dev.restate.sdk.annotation.Service
import dev.restate.sdk.kotlin.*
import domain.apis.*
import domain.models.Fee
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.plugins.defaultheaders.*
import io.ktor.server.plugins.swagger.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

@Service
class TransactionService(
        private val createTransaction: CreateTransaction,
        private val chargeTransaction: ChargeTransaction,
        private val chargeTransactionFee: ChargeTransactionFee,
        private val getTransaction: GetTransaction,
        private val getApplicableFee: GetApplicableFee
) {

    @Handler
    suspend fun create(ctx: Context, request: CreateTransactionRequest): String {

        val transaction =
                ctx.run {
                    createTransaction.execute(
                            request.amount,
                            request.asset,
                            request.assetType,
                            request.type
                    )
                }

        return "transaction created with id: ${transaction.id}"
    }

    @Handler
    suspend fun charge(ctx: Context, id: Long): String {

        val transaction = ctx.run { chargeTransaction.execute(id) }

        return "transaction id: ${transaction.id} is charged"
    }

    @Handler
    suspend fun chargeFees(ctx: Context, id: Long): String {

        val transaction = ctx.run { chargeTransactionFee.execute(id) }

        return "transaction id: ${transaction.id} is charged with fees"
    }

    @Handler
    suspend fun getApplicableFee(ctx: Context, id: Long): List<Fee> {

        val fees = ctx.run { getApplicableFee.execute(id) }

        return fees
    }

    @Handler
    suspend fun getTransaction(ctx: Context, id: Long): String {

        val tx = ctx.run { getTransaction.execute(id) }

        return tx.toString()
    }
}
