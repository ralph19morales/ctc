package domain.transaction.services

import domain.spis.TransactionProvider
import domain.transaction.apis.CreateTransaction
import domain.transaction.models.Asset
import domain.transaction.models.AssetType
import domain.transaction.models.Transaction
import domain.transaction.models.TransactionState
import domain.transaction.models.TransactionType
import java.math.BigDecimal

class CreateTransactionService(val transactionProvider: TransactionProvider) : CreateTransaction {
    override fun execute(
            amount: BigDecimal,
            asset: Asset,
            assetType: AssetType,
            type: TransactionType
    ): Transaction {
        return transactionProvider.createTransaction(
                amount = amount,
                asset = asset,
                assetType = assetType,
                type = type,
                state = TransactionState.PROCESSING,
                totalAmount = amount
        )
    }
}
