package arcanegolem.yms.transactions.domain.usecases

import arcanegolem.yms.core.domain.models.TransactionsTotaledModel
import arcanegolem.yms.transactions.domain.repos.TransactionsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Юзкейс для получения прибыльных транзакций за определенный период с их суммой см. [TransactionsTotaledModel]
 *
 * @param transactionsRepository имплементация [TransactionsRepository]
 */
class LoadIncomesUseCase @Inject constructor(
  private val transactionsRepository: TransactionsRepository
) {
  suspend fun execute(
    periodStartMillis : Long? = null,
    periodEndMillis : Long? = periodStartMillis
  ) : Flow<TransactionsTotaledModel> {
    return transactionsRepository.loadTransactionsForPeriod(periodStartMillis, periodEndMillis, true)
  }
}
