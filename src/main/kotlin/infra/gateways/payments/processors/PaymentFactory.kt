package infra.gateways.payments.processors

import domain.models.AssetType

/**
 * Factory class to create instances of PaymentProcessor based on the asset type. This class is
 * responsible for instantiating the appropriate payment processor implementation based on the asset
 * type (e.g., Crypto or Fiat).
 */
object PaymentFactory {
    fun getProcessor(assetType: AssetType): PaymentProcessor =
            when (assetType) {
                AssetType.CRYPTO -> CryptoPaymentProcessor()
                AssetType.FIAT -> FiatPaymentProcessor()
            }
}
