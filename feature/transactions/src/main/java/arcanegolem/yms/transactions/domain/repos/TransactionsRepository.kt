package arcanegolem.yms.transactions.domain.repos

import arcanegolem.yms.transactions.domain.models.TransactionInfoModel
import arcanegolem.yms.transactions.domain.models.TransactionsTotaledModel

interface TransactionsRepository {
  /**
   * Загружает транзакции связанные с расходами за определенный период с общей суммой
   *
   * @param periodStartMillis начало периода в миллисекундах
   * @param periodEndMillis конец периода в миллисекундах
   *
   * @return общую сумму и список транзакций в виде [TransactionsTotaledModel]
   */
  suspend fun loadExpenses(
    periodStartMillis : Long? = null,
    periodEndMillis : Long? = periodStartMillis
  ) : TransactionsTotaledModel

  /**
   * Загружает транзакции связанные с доходами за определенный период с общей суммой
   *
   * @param periodStartMillis начало периода в миллисекундах
   * @param periodEndMillis конец периода в миллисекундах
   *
   * @return общую сумму и список транзакций в виде [TransactionsTotaledModel]
   */
  suspend fun loadIncomes(
    periodStartMillis: Long? = null,
    periodEndMillis : Long? = periodStartMillis
  ) : TransactionsTotaledModel

  suspend fun updateTransaction(id : Int, model : TransactionInfoModel)

  suspend fun createTransaction(model : TransactionInfoModel)

  suspend fun loadTransaction(id : Int?) : TransactionInfoModel

  suspend fun deleteTransaction(id : Int)
}
