package arcanegolem.yms.domain.repos

import arcanegolem.yms.domain.models.HistoryModel

interface HistoryRepository {
  suspend fun loadHistory(
    isIncome : Boolean,
    periodStartMillis : Long? = null,
    periodEndMillis : Long? = null
  ) : HistoryModel
}