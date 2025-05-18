package domain.transaction.apis

import domain.transaction.models.Transaction

interface ChargeTransaction {
    fun execute(transactionId: Long): Transaction
}
