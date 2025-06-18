package arcanegolem.yms.domain.usecases

import arcanegolem.yms.domain.models.HistoryModel
import arcanegolem.yms.domain.repos.HistoryRepository

class LoadHistoryUseCase(
  private val historyRepository: HistoryRepository
) {
  suspend fun execute(
    isIncome : Boolean,
    periodStart : Long? = null,
    periodEnd : Long? = null
  ) : HistoryModel {
    return historyRepository.loadHistory(isIncome, periodStart, periodEnd)
  }
}