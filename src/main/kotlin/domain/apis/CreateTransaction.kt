package domain.apis

import domain.models.Asset
import domain.models.AssetType
import domain.models.Transaction
import domain.models.TransactionType
import java.math.BigDecimal

interface CreateTransaction {
    fun execute(
            amount: BigDecimal,
            asset: Asset,
            assetType: AssetType,
            type: TransactionType
    ): Transaction
}
