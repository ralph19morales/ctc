package domain.models

import domain.utils.BigDecimalSerializer
import domain.utils.LocalDateSerializer
import java.math.BigDecimal
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

// Data class to represent a transaction
@Serializable
data class Transaction(
        val id: Long,
        @Serializable(with = LocalDateSerializer::class) val createdAt: LocalDateTime? = null,
        @Serializable(with = LocalDateSerializer::class) val updatedAt: LocalDateTime? = null,
        @Serializable(with = BigDecimalSerializer::class) val amount: BigDecimal,
        @Serializable(with = BigDecimalSerializer::class) var paidAmount: BigDecimal,
        @Serializable(with = BigDecimalSerializer::class) var totalAmount: BigDecimal,
        val asset: Asset,
        val assetType: AssetType,
        val type: TransactionType,
        var state: TransactionState,
        var fees: List<Fee> = emptyList(),
)

// Enum class to represent different assets
enum class Asset {
    AED,
    USD,
    EUR,
    PHP,
    BTC,
    ETH,
}

enum class AssetType {
    FIAT,
    CRYPTO
}

// Enum class to represent different transaction types
// This class contains information about various types of transactions that can be performed
enum class TransactionType {
    MOBILE_TOPUP,
    DEPOSIT,
    WITHDRAWAL,
    TRANSFER,
    PAYMENT,
    REFUND
}

// Enum class to represent different states of a transaction
enum class TransactionState(val value: String) {
    FAILED("FAILED"),
    PROCESSING("PROCESSING"),
    SETTLED_PENDING_FEE("SETTLED - PENDING FEE"),
    SETTLED("SETTLED")
}
