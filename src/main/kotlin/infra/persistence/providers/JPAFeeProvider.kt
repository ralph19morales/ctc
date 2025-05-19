package infra.persistence.providers

import domain.models.FeeType as DomainFeeType
import domain.models.TransactionType
import domain.spis.FeeProvider
import infra.configurations.DatabaseFactory
import infra.persistence.entities.FeeType

class JPAFeeProvider : FeeProvider {
    override fun getApplicableFeeTypes(transactionType: TransactionType): List<DomainFeeType> {
        val entityManager = DatabaseFactory.getEntityManager()
        val applicableFees =
                entityManager
                        .createQuery(
                                "SELECT f FROM FeeType f WHERE f.transactionType = :transactionType and f.isActive = true",
                                FeeType::class.java
                        )
                        .setParameter("transactionType", transactionType)
                        .resultList
                        .map { feeType -> feeType.toDomain() }

        return applicableFees
    }
}
