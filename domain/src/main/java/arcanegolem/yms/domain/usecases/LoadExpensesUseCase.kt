package arcanegolem.yms.domain.usecases

import arcanegolem.yms.domain.models.TransactionsTotaledModel
import arcanegolem.yms.domain.repos.TransactionsRepository
import javax.inject.Inject

/**
 * Юзкейс для получения списка расходных транзакций за определенный период с их суммой см. [TransactionsTotaledModel]
 *
 * @param transactionsRepository имплементация [TransactionsRepository]
 */
class LoadExpensesUseCase @Inject constructor(
  private val transactionsRepository: TransactionsRepository
) {
  suspend fun execute(
    periodStartMillis : Long? = null,
    periodEndMillis : Long? = periodStartMillis
  ): TransactionsTotaledModel {
    return transactionsRepository.loadExpenses(periodStartMillis, periodEndMillis)
  }
}
