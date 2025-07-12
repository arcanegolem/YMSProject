package arcanegolem.yms.transactions.domain.usecases

import arcanegolem.yms.transactions.domain.models.TransactionHistoryModel
import arcanegolem.yms.transactions.domain.repos.TransactionsHistoryRepository
import javax.inject.Inject

/**
 * Юзкейс для получения истории транзакций за опредленный период с их суммой и периодом см. [TransactionHistoryModel]
 *
 * @param transactionsHistoryRepository имплементация [TransactionsHistoryRepository]
 */
class LoadTransactionsHistoryUseCase @Inject constructor(
  private val transactionsHistoryRepository: TransactionsHistoryRepository
) {
  suspend fun execute(
    isIncome : Boolean,
    periodStart : Long? = null,
    periodEnd : Long? = null
  ) : TransactionHistoryModel {
    return transactionsHistoryRepository.loadHistory(isIncome, periodStart, periodEnd)
  }
}
