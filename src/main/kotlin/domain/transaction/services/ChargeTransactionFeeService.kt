package domain.transaction.services

import domain.spis.PaymentProvider
import domain.spis.TransactionProvider
import domain.transaction.apis.ChargeTransactionFee
import domain.transaction.apis.GetApplicableFee
import domain.transaction.models.TransactionState

class ChargeTransactionFeeService(
        val transactionProvider: TransactionProvider,
        val getApplicableFee: GetApplicableFee,
        val paymentProvider: PaymentProvider
) : ChargeTransactionFee {
    override fun execute(transactionId: Long): domain.transaction.models.Transaction {
        val transaction = transactionProvider.getTransactionById(transactionId)

        val fees = getApplicableFee.execute(transaction)
        val totalFees = fees.map { it.fee }.reduce { acc, fee -> acc.add(fee) }
        transaction.totalAmount = transaction.totalAmount.add(totalFees)
        transaction.fees = fees
        transactionProvider.updateTransaction(transaction)

        val isSuccess =
                paymentProvider.processPayment(
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
