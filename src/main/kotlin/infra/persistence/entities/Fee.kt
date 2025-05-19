package infra.persistence.entities

import domain.models.Asset
import domain.models.Fee as DomainFee
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
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
        @Enumerated(EnumType.STRING) var asset: Asset,
        var amount: BigDecimal,
        @Column(precision = 10, scale = 5) var rate: BigDecimal,
) : BaseEntity() {
    fun toDomain(): DomainFee {
        return DomainFee(
                id = id,
                createdAt = createdAt.toKotlinDateTime(),
                updatedAt = (updatedAt ?: createdAt).toKotlinDateTime(),
                transactionId = transaction.id ?: 0L,
                asset = asset,
                type = type.toDomain(),
                amount = amount,
                rate = rate,
        )
    }

    override fun toString(): String {
        return "Fee(transactionId=${transaction.id}, asset=$asset, type=$type, amount=$amount, rate=$rate,"
    }
}
