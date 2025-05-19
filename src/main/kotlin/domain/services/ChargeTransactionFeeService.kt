package domain.services

import domain.apis.ChargeTransactionFee
import domain.apis.GetApplicableFee
import domain.models.Transaction
import domain.models.TransactionState
import domain.spis.PaymentProvider
import domain.spis.TransactionProvider

class ChargeTransactionFeeService(
        val transactionProvider: TransactionProvider,
        val getApplicableFee: GetApplicableFee,
        val paymentProvider: PaymentProvider
) : ChargeTransactionFee {
    override fun execute(transactionId: Long): Transaction {
        val transaction = transactionProvider.getTransactionById(transactionId)

        val fees = getApplicableFee.execute(transactionId)
        val totalFees = fees.map { it.amount }.reduce { acc, fee -> acc.add(fee) }
        transaction.totalAmount = transaction.totalAmount.add(totalFees)
        transaction.fees = fees
        transactionProvider.updateTransaction(transaction)

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

        transaction.state = TransactionState.SETTLED
        return transactionProvider.updateTransaction(transaction)
    }
}
