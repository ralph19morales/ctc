package infra.persistence.entities

import domain.models.FeeCategory
import domain.models.FeeType as DomainFeeType
import domain.models.TransactionType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Table
import jakarta.persistence.UniqueConstraint
import java.math.BigDecimal

@Entity
@Table(name = "fee_types", uniqueConstraints = [UniqueConstraint(columnNames = ["name"])])
data class FeeType(
        var name: String,
        var description: String?,
        @Enumerated(EnumType.STRING) var transactionType: TransactionType,
        @Enumerated(EnumType.STRING) var category: FeeCategory,
        @Column(precision = 10, scale = 5) var rate: BigDecimal,
        var isActive: Boolean
) : BaseEntity() {
    fun toDomain(): DomainFeeType {
        return DomainFeeType(
                id = id ?: 0L,
                createdAt = createdAt.toKotlinDateTime(),
                updatedAt = (updatedAt ?: createdAt).toKotlinDateTime(),
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
