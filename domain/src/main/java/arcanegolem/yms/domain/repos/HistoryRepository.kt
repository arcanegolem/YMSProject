package arcanegolem.yms.domain.repos

import arcanegolem.yms.domain.models.HistoryModel

interface HistoryRepository {
  /**
   * Загружает историю транзакций за определенный период
   *
   * @param isIncome флаг прибыль(true)/расход(false)
   * @param periodStartMillis старт периода в миллисекундах
   * @param periodEndMillis конец периода в миллисекундах
   *
   * @return историю транзакций в виде [HistoryModel]
   */
  suspend fun loadHistory(
    isIncome : Boolean,
    periodStartMillis : Long? = null,
    periodEndMillis : Long? = null
  ) : HistoryModel
}