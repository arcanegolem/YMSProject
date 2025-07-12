package arcanegolem.yms.transactions.domain.usecases

import arcanegolem.yms.transactions.domain.models.TransactionInfoModel
import arcanegolem.yms.transactions.domain.repos.TransactionsRepository
import javax.inject.Inject

class CreateTransactionUseCase @Inject constructor(
  private val transactionsRepository: TransactionsRepository
) {
  suspend fun execute(model: TransactionInfoModel) {
    transactionsRepository.createTransaction(model)
  }
}