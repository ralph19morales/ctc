package domain.transaction.services

import domain.spis.FeeProvider
import domain.transaction.apis.GetApplicableFee
import domain.transaction.models.Fee
import domain.transaction.models.Transaction
import java.math.BigDecimal
import java.math.RoundingMode

class GetApplicableFeeService(val feeProvider: FeeProvider) : GetApplicableFee {
    override fun execute(transaction: Transaction): List<Fee> {
        val feeTypes = feeProvider.getApplicableFeeTypes(transaction.type)
        return feeTypes.map { feeType ->
            Fee(
                    id = feeType.id,
                    name = feeType.name,
                    category = feeType.category,
                    createdAt = transaction.createdAt,
                    updatedAt = transaction.updatedAt,
                    description = feeType.description ?: "",
                    transactionId = transaction.id,
                    type = feeType,
                    asset = transaction.asset,
                    rate = feeType.rate,
                    fee = calculateFeeAmount(transaction.amount, feeType.rate)
            )
        }
    }

    fun calculateFeeAmount(amount: BigDecimal, rate: BigDecimal): BigDecimal {
        // calculate the fee amount based on the transaction amount and fee type rate
        return amount.multiply(rate).setScale(2, RoundingMode.HALF_UP)
    }
}
