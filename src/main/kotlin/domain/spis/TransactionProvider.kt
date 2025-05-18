package domain.spis

import domain.transaction.models.Asset
import domain.transaction.models.AssetType
import domain.transaction.models.Transaction
import domain.transaction.models.TransactionState
import domain.transaction.models.TransactionType
import java.math.BigDecimal

interface TransactionProvider {

    // Transaction creation
    fun createTransaction(
            amount: BigDecimal,
            totalAmount: BigDecimal,
            asset: Asset,
            assetType: AssetType,
            type: TransactionType,
            state: TransactionState
    ): Transaction

    // Get transaction by ID. This method should return the transaction with the specified ID.
    fun getTransactionById(transactionID: Long): Transaction

    // Update transaction
    fun updateTransaction(transaction: Transaction): Transaction
}
