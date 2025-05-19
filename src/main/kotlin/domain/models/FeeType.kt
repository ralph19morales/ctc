package domain.models

import java.math.BigDecimal
import java.time.LocalDateTime

// Data class to represent a fee type
// This class contains information about different types of fees that can be applied to transactions
data class FeeType(
        val id: Long,
        val createdAt: LocalDateTime,
        val updatedAt: LocalDateTime,
        val name: String,
        val description: String?,
        val transactionType: TransactionType,
        val category: FeeCategory,
        val rate: BigDecimal,
        val isActive: Boolean // fee is not applied if false
)

// Enum class to categorize different types of fees
enum class FeeCategory {
    SERVICE,
    PROCESSING,
    LATE_PAYMENT,
    CANCELLATION,
    REFUND,
    OTHER
}
