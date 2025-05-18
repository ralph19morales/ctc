package domain.transaction.apis

import domain.transaction.models.Asset
import domain.transaction.models.AssetType
import domain.transaction.models.Transaction
import domain.transaction.models.TransactionType
import java.math.BigDecimal

interface CreateTransaction {
    fun execute(
            amount: BigDecimal,
            asset: Asset,
            assetType: AssetType,
            type: TransactionType
    ): Transaction
}
