package arcanegolem.yms.domain.usecases

import arcanegolem.yms.domain.models.TransactionsTotaledModel
import arcanegolem.yms.domain.repos.TransactionsRepository

/**
 * Юзкейс для получения прибыльных транзакций за определенный период с их суммой см. [TransactionsTotaledModel]
 *
 * @param transactionsRepository имплементация [TransactionsRepository]
 */
class LoadIncomesUseCase(
  private val transactionsRepository: TransactionsRepository
) {
  suspend fun execute(
    periodStartMillis : Long? = null,
    periodEndMillis : Long? = periodStartMillis
  ) : TransactionsTotaledModel {
    return transactionsRepository.loadIncomes(periodStartMillis, periodEndMillis)
  }
}
