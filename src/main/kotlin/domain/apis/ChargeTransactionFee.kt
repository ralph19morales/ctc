package domain.apis

import domain.models.Transaction

interface ChargeTransactionFee {
    fun execute(transactionId: Long): Transaction
}
