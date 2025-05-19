package infra.persistence.providers

import domain.models.Asset
import domain.models.AssetType
import domain.models.Transaction as DomainTransaction
import domain.models.TransactionState
import domain.models.TransactionType
import domain.spis.TransactionProvider
import infra.configurations.DatabaseFactory
import infra.persistence.entities.Fee
import infra.persistence.entities.FeeType
import infra.persistence.entities.Transaction
import java.math.BigDecimal

class JPATransactionProvider : TransactionProvider {

        override fun createTransaction(
                amount: BigDecimal,
                paidAmount: BigDecimal,
                totalAmount: BigDecimal,
                asset: Asset,
                assetType: AssetType,
                type: TransactionType,
                state: TransactionState
        ): DomainTransaction {
                val entityManager = DatabaseFactory.getEntityManager()
                entityManager.transaction.begin()
                val tx =
                        Transaction(
                                amount = amount,
                                paidAmount = paidAmount,
                                totalAmount = totalAmount,
                                asset = asset,
                                assetType = assetType,
                                type = type,
                                state = state
                        )

                entityManager.persist(tx)
                entityManager.transaction.commit()

                return tx.toDomain()
        }

        override fun getTransactionById(transactionID: Long): DomainTransaction {
                val entityManager = DatabaseFactory.getEntityManager()
                val tx =
                        entityManager
                                .createQuery(
                                        "SELECT t FROM Transaction t WHERE t.id = :transactionID",
                                        Transaction::class.java
                                )
                                .setParameter("transactionID", transactionID)
                                .singleResult

                return tx.toDomain()
        }

        override fun updateTransaction(transaction: DomainTransaction): DomainTransaction {
                val entityManager = DatabaseFactory.getEntityManager()
                entityManager.transaction.begin()

                val tx =
                        entityManager
                                .createQuery(
                                        "SELECT t FROM Transaction t WHERE t.id = :transactionID",
                                        Transaction::class.java
                                )
                                .setParameter("transactionID", transaction.id)
                                .singleResult

                tx.amount = transaction.amount
                tx.paidAmount = transaction.paidAmount
                tx.totalAmount = transaction.totalAmount
                tx.asset = transaction.asset
                tx.assetType = transaction.assetType
                tx.type = transaction.type
                tx.state = transaction.state

                // Update fees
                if (tx.fees.isNotEmpty()) {
                        val fees =
                                transaction.fees.map {
                                        val feeType =
                                                entityManager
                                                        .createQuery(
                                                                "SELECT f FROM FeeType f WHERE f.id = :feeTypeID",
                                                                FeeType::class.java
                                                        )
                                                        .setParameter("feeTypeID", it.type.id)
                                                        .singleResult
                                        Fee(
                                                amount = it.amount,
                                                rate = it.rate,
                                                asset = it.asset,
                                                type = feeType,
                                                transaction = tx
                                        )
                                }
                        entityManager.persist(fees)
                        tx.fees = fees
                }

                entityManager.persist(tx)
                entityManager.transaction.commit()

                return tx.toDomain()
        }
}
