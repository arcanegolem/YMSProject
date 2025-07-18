package arcanegolem.yms.transactions.data.repos


import arcanegolem.yms.core.data.utils.monthStartMillis
import arcanegolem.yms.core.data.utils.todayMillis
import arcanegolem.yms.transactions.domain.models.TransactionHistoryModel
import arcanegolem.yms.transactions.domain.repos.TransactionsHistoryRepository
import arcanegolem.yms.transactions.domain.usecases.LoadExpensesUseCase
import arcanegolem.yms.transactions.domain.usecases.LoadIncomesUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * Реальная имплементация репозитория истории
 *
 * @param loadIncomesUseCase юзкейс для подгрузки данных о прибыльных транзакциях
 * @param loadExpensesUseCase юзкейс для подгрузки данных о расходных транзакциях
 */
 class TransactionsHistoryRepositoryImpl @Inject constructor(
  private val loadIncomesUseCase: LoadIncomesUseCase,
  private val loadExpensesUseCase: LoadExpensesUseCase
) : TransactionsHistoryRepository {
  override suspend fun loadHistory(
    isIncome: Boolean,
    periodStartMillis: Long?,
    periodEndMillis: Long?
  ): Flow<TransactionHistoryModel> {
    val transactions = if (isIncome)
      loadIncomesUseCase.execute(periodStartMillis, periodEndMillis)
    else loadExpensesUseCase.execute(periodStartMillis, periodEndMillis)

    return transactions.map {
      TransactionHistoryModel(
        transactionsTotaled = it,
        periodStart = periodStartMillis ?: monthStartMillis(),
        periodEnd = periodEndMillis ?: todayMillis(),
        isIncome = isIncome
      )
    }
  }
}
