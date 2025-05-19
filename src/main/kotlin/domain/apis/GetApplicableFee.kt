package domain.apis

import domain.models.Fee

interface GetApplicableFee {
    fun execute(transactionId: Long): List<Fee>
}
