package infra.persistence.entities

import domain.models.Asset
import domain.models.AssetType
import domain.models.Transaction as DomainTransaction
import domain.models.TransactionState
import domain.models.TransactionType
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.OneToMany
import jakarta.persistence.Table
import java.math.BigDecimal
import kotlinx.datetime.*
import org.hibernate.annotations.Cascade
import org.hibernate.annotations.CascadeType

@Entity
@Table(name = "transactions")
data class Transaction(
        var amount: BigDecimal,
        var paidAmount: BigDecimal,
        var totalAmount: BigDecimal,
        @Enumerated(EnumType.STRING) var asset: Asset,
        @Enumerated(EnumType.STRING) var assetType: AssetType,
        @Enumerated(EnumType.STRING) var type: TransactionType,
        @Enumerated(EnumType.STRING) var state: TransactionState,
        @OneToMany(mappedBy = "transaction")
        @Cascade(CascadeType.ALL)
        var fees: List<Fee> = emptyList(),
) : BaseEntity() {
    fun toDomain(): DomainTransaction {
        return DomainTransaction(
                id = id ?: 0L,
                createdAt = createdAt.toKotlinDateTime(),
                updatedAt = (updatedAt ?: createdAt).toKotlinDateTime(),
                amount = amount,
                paidAmount = paidAmount,
                totalAmount = totalAmount,
                asset = asset,
                assetType = assetType,
                type = type,
                state = state,
                fees = fees.map { it.toDomain() }
        )
    }

    override fun toString(): String {
        return "Transaction(amount=$amount, paidAmount=$paidAmount, totalAmount=$totalAmount, asset=$asset, assetType=$assetType, type=$type, state=$state, fees=$fees)"
    }
}
