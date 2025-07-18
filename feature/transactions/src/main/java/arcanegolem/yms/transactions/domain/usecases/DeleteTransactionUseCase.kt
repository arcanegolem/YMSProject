package arcanegolem.yms.transactions.domain.usecases

import arcanegolem.yms.transactions.domain.repos.TransactionsRepository
import javax.inject.Inject

class DeleteTransactionUseCase @Inject constructor(
  private val transactionsRepository: TransactionsRepository
) {
  suspend fun execute(id : Int, isArbitrary : Boolean?) {
    transactionsRepository.deleteTransaction(id, isArbitrary)
  }
}