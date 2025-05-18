package domain.transaction.services

import domain.spis.PaymentProvider
import domain.spis.TransactionProvider
import domain.transaction.apis.ChargeTransaction
import domain.transaction.models.Transaction
import domain.transaction.models.TransactionState

class ChargeTransactionService(
        val transactionProvider: TransactionProvider,
        val paymentProvider: PaymentProvider
) : ChargeTransaction {
    override fun execute(transactionId: Long): Transaction {
        val transaction = transactionProvider.getTransactionById(transactionId)

        val isSuccess =
                paymentProvider.processPayment(
                        transaction.totalAmount,
                        transaction.asset,
                        transaction.assetType
                )
        if (!isSuccess) {
            transaction.state = TransactionState.FAILED
        }

        if (transaction.fees.isNotEmpty()
        ) { // Check if charge include fees, if there is, set to SETTLED
            transaction.state = TransactionState.SETTLED
        } else {
            transaction.state = TransactionState.SETTLED_PENDING_FEE
        }

        return transactionProvider.updateTransaction(transaction)
    }
}
