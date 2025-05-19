package domain.apis

import domain.models.Transaction

interface ChargeTransaction {
    fun execute(transactionId: Long): Transaction
}
