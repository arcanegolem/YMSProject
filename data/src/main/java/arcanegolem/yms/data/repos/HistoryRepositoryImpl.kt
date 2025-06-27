package arcanegolem.yms.data.repos

import arcanegolem.yms.data.util.monthStartMillis
import arcanegolem.yms.data.util.todayMillis
import arcanegolem.yms.domain.models.HistoryModel
import arcanegolem.yms.domain.repos.HistoryRepository
import arcanegolem.yms.domain.usecases.LoadExpensesUseCase
import arcanegolem.yms.domain.usecases.LoadIncomesUseCase

/**
 * Реальная имплементация репозитория истории
 *
 * @param loadIncomesUseCase юзкейс для подгрузки данных о прибыльных транзакциях
 * @param loadExpensesUseCase юзкейс для подгрузки данных о расходных транзакциях
 */
internal class HistoryRepositoryImpl(
  private val loadIncomesUseCase: LoadIncomesUseCase,
  private val loadExpensesUseCase: LoadExpensesUseCase
) : HistoryRepository {
  override suspend fun loadHistory(
    isIncome: Boolean,
    periodStartMillis: Long?,
    periodEndMillis: Long?
  ): HistoryModel {
    val transactions = if (isIncome)
      loadIncomesUseCase.execute(periodStartMillis, periodEndMillis)
    else loadExpensesUseCase.execute(periodStartMillis, periodEndMillis)

    return HistoryModel(
      transactionsTotaled = transactions,
      periodStart = periodStartMillis ?: monthStartMillis(),
      periodEnd = periodEndMillis ?: todayMillis(),
      isIncome = isIncome
    )
  }
}
