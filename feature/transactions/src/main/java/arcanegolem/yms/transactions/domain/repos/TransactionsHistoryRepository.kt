package arcanegolem.yms.transactions.domain.repos

import arcanegolem.yms.transactions.domain.models.TransactionHistoryModel

interface TransactionsHistoryRepository {
  /**
   * Загружает историю транзакций за определенный период
   *
   * @param isIncome флаг прибыль(true)/расход(false)
   * @param periodStartMillis старт периода в миллисекундах
   * @param periodEndMillis конец периода в миллисекундах
   *
   * @return историю транзакций в виде [TransactionHistoryModel]
   */
  suspend fun loadHistory(
    isIncome : Boolean,
    periodStartMillis : Long? = null,
    periodEndMillis : Long? = null
  ) : TransactionHistoryModel
}
