package domain.spis

import domain.transaction.models.Asset
import domain.transaction.models.AssetType
import java.math.BigDecimal

interface PaymentProvider {
    fun processPayment(amount: BigDecimal, asset: Asset, assetType: AssetType): Boolean
    fun refundPayment(transactionId: Long, amount: BigDecimal): Boolean
    fun getTransactionStatus(transactionId: Long): PaymentStatus
}

enum class PaymentStatus {
    PENDING,
    COMPLETED,
    FAILED,
    REFUNDED
}
