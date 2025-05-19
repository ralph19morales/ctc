package domain.spis

import domain.models.Asset
import domain.models.AssetType
import java.math.BigDecimal

interface PaymentProvider {
    fun processPayment(
            transactionId: Long,
            amount: BigDecimal,
            asset: Asset,
            assetType: AssetType
    ): Boolean
    fun refundPayment(transactionId: Long, amount: BigDecimal): Boolean
    fun getTransactionStatus(transactionId: Long): PaymentStatus
}

enum class PaymentStatus {
    PENDING,
    COMPLETED,
    FAILED,
    REFUNDED
}
