package infra.configurations

import domain.apis.*
import domain.services.*
import domain.spis.*
import infra.gateways.payments.PaymentGateway
import infra.persistence.providers.*
import org.koin.dsl.module

/**
 * This val is responsible for configuring the domain layer of the application. It initializes the
 * necessary services and providers using dependency injection.
 */
val domainModules = module {

    // initialize apis
    single<GetTransaction> { GetTransactionService(get()) }
    single<CreateTransaction> { CreateTransactionService(get()) }
    single<ChargeTransaction> { ChargeTransactionService(get(), get()) }
    single<ChargeTransactionFee> { ChargeTransactionFeeService(get(), get(), get()) }
    single<GetApplicableFee> { GetApplicableFeeService(get(), get()) }

    // initialize spis
    single<TransactionProvider> { JPATransactionProvider() }
    single<FeeProvider> { JPAFeeProvider() }
    single<PaymentProvider> { PaymentGateway() }
}
