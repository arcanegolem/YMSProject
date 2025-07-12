package arcanegolem.yms.transactions.domain.usecases

import arcanegolem.yms.transactions.domain.models.TransactionInfoModel
import arcanegolem.yms.transactions.domain.repos.TransactionsRepository
import javax.inject.Inject

class LoadTransactionUseCase @Inject constructor(
  private val transactionsRepository: TransactionsRepository
) {
  suspend fun execute(id : Int? = null) : TransactionInfoModel {
    return transactionsRepository.loadTransaction(id)
  }
}