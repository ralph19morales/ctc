package infra.persistence.entities

import domain.models.Asset
import domain.models.Fee as DomainFee
import domain.models.FeeCategory
import jakarta.persistence.Entity
import jakarta.persistence.Table
import java.math.BigDecimal

@Entity
@Table(name = "fees")
class Fee(
        @jakarta.persistence.ManyToOne
        @jakarta.persistence.JoinColumn(name = "transaction_id")
        var transaction: Transaction,
        @jakarta.persistence.ManyToOne
        @jakarta.persistence.JoinColumn(name = "fee_type_id")
        var type: FeeType,
        var asset: Asset,
        var amount: BigDecimal,
        var rate: BigDecimal,
        var name: String,
        var description: String,
        var category: FeeCategory,
) : BaseEntity() {
    fun toDomain(): DomainFee {
        return DomainFee(
                id = id,
                createdAt = createdAt,
                updatedAt = updatedAt ?: createdAt,
                transactionId = transaction.id ?: 0L,
                asset = asset,
                type = type.toDomain(),
                amount = amount,
                rate = rate,
                name = name,
                description = description,
                category = category
        )
    }

    override fun toString(): String {
        return "Fee(transactionId=${transaction.id}, asset=$asset, type=$type, amount=$amount, rate=$rate, name='$name', description='$description', category=$category)"
    }
}
