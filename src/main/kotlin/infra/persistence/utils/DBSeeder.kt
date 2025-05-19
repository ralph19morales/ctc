package infra.persistence.utils

import infra.configurations.DatabaseFactory

/**
 * This function seeds the database with initial data for the fee_types table. It inserts three fee
 * types with different names, descriptions, transaction types, and active status. If a fee type
 * with the same name already exists, it will not be inserted again. For production use, there
 * should be CRUD operations to manage fee types.
 */
fun seedFeeTypes() {
    val entityManager = DatabaseFactory.getEntityManager()
    entityManager.transaction.begin()

    entityManager
            .createNativeQuery(
                    """
        INSERT INTO fee_types (name, description, transactionType, category, isActive, rate)
        VALUES
            ('Transfer Service Fee', 'Standard service fee rate for transfers', 'TRANSFER', 'SERVICE', true, '0.0012'),
            ('Payment Service Fee', 'Standard service fee rate for payments', 'PAYMENT', 'SERVICE', true, '0.0012'),
            ('Mobile Top Up Service Fee', 'Standard service fee rate for mobile top up', 'MOBILE_TOPUP', 'SERVICE', true, '0.0012'),
            ('Mobile Top Up Processing Fee', 'Standard processing fee rate for mobile top up', 'MOBILE_TOPUP', 'PROCESSING', true, '0.0015'),
            ('Mobile Top Up Miscellaneous Fee', 'Standard service miscellaneous rate for mobile top up', 'MOBILE_TOPUP', 'PROCESSING', false, '0.0015')
        ON CONFLICT (name) DO NOTHING;
    """.trimIndent()
            )
            .executeUpdate()

    entityManager.transaction.commit()
    entityManager.close()
}
