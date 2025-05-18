package domain.transaction.apis

import domain.transaction.models.Fee
import domain.transaction.models.Transaction

interface GetApplicableFee {
    fun execute(transaction: Transaction): List<Fee>
}
