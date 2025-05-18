package domain.transaction.models

import java.math.BigDecimal
import java.time.LocalDateTime

// Data class to represent a fee associated with a transaction
data class Fee(
        val id: Long,
        val createdAt: LocalDateTime,
        val updatedAt: LocalDateTime,
        val transactionId: Long,
        val asset: Asset,
        val type: FeeType,
        val fee: BigDecimal,
        val rate: BigDecimal,
        val name: String,
        val description: String,
        val category: FeeCategory,
)
