package infra.persistence.entities

import domain.models.FeeCategory
import domain.models.FeeType as DomainFeeType
import domain.models.TransactionType
import jakarta.persistence.Entity
import jakarta.persistence.Table
import java.math.BigDecimal

@Entity
@Table(name = "fee_types")
data class FeeType(
        var name: String,
        var description: String?,
        var transactionType: TransactionType,
        var category: FeeCategory,
        var rate: BigDecimal,
        var isActive: Boolean
) : BaseEntity() {
    fun toDomain(): DomainFeeType {
        return DomainFeeType(
                id = id ?: 0L,
                createdAt = createdAt,
                updatedAt = updatedAt ?: createdAt,
                name = name,
                description = description,
                transactionType = transactionType,
                category = category,
                rate = rate,
                isActive = isActive
        )
    }

    override fun toString(): String {
        return "FeeType(name='$name', description=$description, transactionType=$transactionType, category=$category, rate=$rate, isActive=$isActive)"
    }
}
