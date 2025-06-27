package arcanegolem.yms.domain.repos

import arcanegolem.yms.domain.models.TransactionsTotaledModel

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
}
