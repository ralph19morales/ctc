package infra.gateways.payments.processors

import domain.models.Asset
import java.math.BigDecimal

interface PaymentProcessor {
    fun processPayment(transactionID: Long, amount: BigDecimal, asset: Asset): Boolean
}
