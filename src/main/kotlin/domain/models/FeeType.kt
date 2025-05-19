package domain.models

import domain.utils.BigDecimalSerializer
import domain.utils.LocalDateSerializer
import java.math.BigDecimal
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

// Data class to represent a fee type
// This class contains information about different types of fees that can be applied to transactions
@Serializable
data class FeeType(
        val id: Long,
        @Serializable(with = LocalDateSerializer::class) val createdAt: LocalDateTime? = null,
        @Serializable(with = LocalDateSerializer::class) val updatedAt: LocalDateTime? = null,
        val name: String,
        val description: String?,
        val transactionType: TransactionType,
        val category: FeeCategory,
        @Serializable(with = BigDecimalSerializer::class) val rate: BigDecimal,
        val isActive: Boolean // fee is not applied if false
)

// Enum class to categorize different types of fees
enum class FeeCategory {
    SERVICE,
    PROCESSING,
    LATE_PAYMENT,
    CANCELLATION,
    REFUND,
    MISCELLANEOUS,
}
