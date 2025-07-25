package arcanegolem.yms.transactions.domain.repos

import arcanegolem.yms.transactions.domain.models.TransactionInfoModel
import arcanegolem.yms.core.domain.models.TransactionsTotaledModel
import kotlinx.coroutines.flow.Flow

interface TransactionsRepository {
  /**
   * Загружает транзакции связанные за определенный период с общей суммой
   *
   * @param periodStartMillis начало периода в миллисекундах
   * @param periodEndMillis конец периода в миллисекундах
   * @param isIncome флаг доходов
   *
   * @return общую сумму и список транзакций в виде [TransactionsTotaledModel]
   */
  suspend fun loadTransactionsForPeriod(
    periodStartMillis: Long?,
    periodEndMillis: Long?,
    isIncome : Boolean
  ) : Flow<TransactionsTotaledModel>

  suspend fun updateTransaction(id : Int, model : TransactionInfoModel)

  suspend fun createTransaction(model : TransactionInfoModel)

  suspend fun loadTransaction(id : Int?, isArbitrary : Boolean?) : TransactionInfoModel

  suspend fun deleteTransaction(id : Int, isArbitrary: Boolean?)
}
