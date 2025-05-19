package domain.models

import domain.utils.BigDecimalSerializer
import domain.utils.LocalDateSerializer
import java.math.BigDecimal
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

// Data class to represent a fee associated with a transaction
@Serializable
data class Fee(
        val id: Long? = null,
        @Serializable(with = LocalDateSerializer::class) val createdAt: LocalDateTime? = null,
        @Serializable(with = LocalDateSerializer::class) val updatedAt: LocalDateTime? = null,
        val transactionId: Long,
        val asset: Asset,
        val type: FeeType,
        @Serializable(with = BigDecimalSerializer::class) val amount: BigDecimal,
        @Serializable(with = BigDecimalSerializer::class) val rate: BigDecimal,
)
