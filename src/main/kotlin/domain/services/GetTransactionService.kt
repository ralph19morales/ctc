package domain.services

import domain.apis.GetTransaction
import domain.models.Transaction
import domain.spis.TransactionProvider

class GetTransactionService(
        val transactionProvider: TransactionProvider,
) : GetTransaction {
    override fun execute(transactionId: Long): Transaction {
        return transactionProvider.getTransactionById(transactionId)
    }
}
