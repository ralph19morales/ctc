package domain.spis

import domain.models.FeeType
import domain.models.TransactionType

interface FeeProvider {

    // This method should return a list of applicable fee types for the given transaction type.
    fun getApplicableFeeTypes(transactionType: TransactionType): List<FeeType>
}
