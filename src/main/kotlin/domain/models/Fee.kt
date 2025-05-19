package domain.models

import java.math.BigDecimal
import java.time.LocalDateTime

// Data class to represent a fee associated with a transaction
data class Fee(
        val id: Long? = null,
        val createdAt: LocalDateTime? = null,
        val updatedAt: LocalDateTime? = null,
        val transactionId: Long,
        val asset: Asset,
        val type: FeeType,
        val amount: BigDecimal,
        val rate: BigDecimal,
        val name: String,
        val description: String,
        val category: FeeCategory,
)
