package domain.services

import domain.apis.ChargeTransaction
import domain.models.Transaction
import domain.models.TransactionState
import domain.spis.PaymentProvider
import domain.spis.TransactionProvider

class ChargeTransactionService(
        val transactionProvider: TransactionProvider,
        val paymentProvider: PaymentProvider
) : ChargeTransaction {
    override fun execute(transactionId: Long): Transaction {
        val transaction = transactionProvider.getTransactionById(transactionId)

        val isSuccess =
                paymentProvider.processPayment(
                        transactionId,
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
