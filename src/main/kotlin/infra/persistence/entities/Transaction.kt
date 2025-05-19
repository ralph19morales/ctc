package infra.persistence.entities

import domain.models.Asset
import domain.models.AssetType
import domain.models.Transaction as DomainTransaction
import domain.models.TransactionState
import domain.models.TransactionType
import jakarta.persistence.Entity
import jakarta.persistence.OneToMany
import jakarta.persistence.Table
import java.math.BigDecimal
import org.hibernate.annotations.Cascade
import org.hibernate.annotations.CascadeType

@Entity
@Table(name = "transactions")
data class Transaction(
        var amount: BigDecimal,
        var totalAmount: BigDecimal,
        var asset: Asset,
        var assetType: AssetType,
        var type: TransactionType,
        var state: TransactionState,
        @OneToMany(mappedBy = "transaction")
        @Cascade(CascadeType.ALL)
        var fees: List<Fee> = emptyList(),
) : BaseEntity() {
    fun toDomain(): DomainTransaction {
        return DomainTransaction(
                id = id ?: 0L,
                createdAt = createdAt,
                updatedAt = updatedAt ?: createdAt,
                amount = amount,
                asset = asset,
                assetType = assetType,
                type = type,
                state = state,
                totalAmount = totalAmount,
                fees = fees.map { it.toDomain() }
        )
    }

    override fun toString(): String {
        return "Transaction(amount=$amount, totalAmount=$totalAmount, asset=$asset, assetType=$assetType, type=$type, state=$state, fees=$fees)"
    }
}
