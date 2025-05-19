package domain.services

import domain.apis.CreateTransaction
import domain.models.Asset
import domain.models.AssetType
import domain.models.Transaction
import domain.models.TransactionState
import domain.models.TransactionType
import domain.spis.TransactionProvider
import java.math.BigDecimal

class CreateTransactionService(val transactionProvider: TransactionProvider) : CreateTransaction {
    override fun execute(
            amount: BigDecimal,
            asset: Asset,
            assetType: AssetType,
            type: TransactionType,
    ): Transaction {
        return transactionProvider.createTransaction(
                amount = amount,
                asset = asset,
                assetType = assetType,
                type = type,
                state = TransactionState.PROCESSING,
                totalAmount = amount,
        )
    }
}
