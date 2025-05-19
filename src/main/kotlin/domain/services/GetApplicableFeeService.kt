package domain.services

import domain.apis.GetApplicableFee
import domain.models.Fee
import domain.spis.FeeProvider
import domain.spis.TransactionProvider
import java.math.BigDecimal
import java.math.RoundingMode

class GetApplicableFeeService(
        val transactionProvider: TransactionProvider,
        val feeProvider: FeeProvider
) : GetApplicableFee {
    override fun execute(transactionId: Long): List<Fee> {
        val transaction = transactionProvider.getTransactionById(transactionId)
        val feeTypes = feeProvider.getApplicableFeeTypes(transaction.type)
        return feeTypes.map { feeType ->
            Fee(
                    createdAt = transaction.createdAt,
                    updatedAt = transaction.updatedAt,
                    transactionId = transaction.id,
                    type = feeType,
                    asset = transaction.asset,
                    rate = feeType.rate,
                    amount = calculateFeeAmount(transaction.amount, feeType.rate)
            )
        }
    }

    fun calculateFeeAmount(amount: BigDecimal, rate: BigDecimal): BigDecimal {
        // calculate the fee amount based on the transaction amount and fee type rate
        return amount.multiply(rate).setScale(2, RoundingMode.HALF_UP)
    }
}
