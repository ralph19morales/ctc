package domain.apis

import domain.models.Transaction

interface GetTransaction {
    fun execute(transactionId: Long): Transaction
}
