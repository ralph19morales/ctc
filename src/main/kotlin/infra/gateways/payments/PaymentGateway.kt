package infra.gateways.payments

import domain.models.Asset
import domain.models.AssetType
import domain.spis.PaymentProvider
import domain.spis.PaymentStatus
import infra.gateways.payments.processors.PaymentFactory
import java.math.BigDecimal

/**
 * PaymentGateway is a class that implements the PaymentProvider interface. It is responsible for
 * processing payments, refunding payments, and getting transaction status.
 */
class PaymentGateway : PaymentProvider {
    override fun processPayment(
            transactionId: Long,
            amount: BigDecimal,
            asset: Asset,
            assetType: AssetType
    ): Boolean {
        return PaymentFactory.getProcessor(assetType).processPayment(transactionId, amount, asset)
    }

    override fun refundPayment(transactionId: Long, amount: BigDecimal): Boolean {
        // Implement the logic to refund the payment
        return true
    }

    override fun getTransactionStatus(transactionId: Long): PaymentStatus {
        // Implement the logic to get the transaction status
        return PaymentStatus.COMPLETED
    }
}
