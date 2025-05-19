package infra.gateways.payments.processors

import com.ralphmorales.logger
import domain.models.Asset
import java.math.BigDecimal

/**
 * This class implements the PaymentProcessor interface for processing fiat payments. It contains
 * the logic to handle fiat transactions, including currency conversion and payment validation.
 */
class CryptoPaymentProcessor : PaymentProcessor {
    override fun processPayment(transactionID: Long, amount: BigDecimal, asset: Asset): Boolean {
        // Implement the logic to process fiat payments here
        // This could include currency conversion, payment validation, etc.
        logger.info(
                "Processing crypto payment for transaction ID: $transactionID, asset: $asset, amount: $amount"
        )
        return true // Return true if the payment was successful, false otherwise
    }
}
