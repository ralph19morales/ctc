package domain.transaction.apis

import domain.transaction.models.Transaction

interface ChargeTransactionFee {
    fun execute(transactionId: Long): Transaction
}
