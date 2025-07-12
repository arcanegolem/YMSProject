package arcanegolem.yms.transactions.domain.usecases

import arcanegolem.yms.transactions.domain.models.TransactionInfoModel
import arcanegolem.yms.transactions.domain.repos.TransactionsRepository
import javax.inject.Inject

class UpdateTransactionUseCase @Inject constructor(
  private val transactionsRepository: TransactionsRepository
) {
  suspend fun execute(transactionId: Int, model: TransactionInfoModel) {
    transactionsRepository.updateTransaction(transactionId, model)
  }
}