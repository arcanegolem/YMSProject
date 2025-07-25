package arcanegolem.yms.account.domain.usecases

import arcanegolem.yms.account.domain.models.TransactionsByDayOfMonthModel
import arcanegolem.yms.account.domain.repos.AccountRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LoadThisMonthTransactionsForAccountUseCase @Inject constructor(
  private val accountRepository: AccountRepository
) {
  suspend fun execute() : Flow<TransactionsByDayOfMonthModel> {
    return accountRepository.getAccountTransactionsForThisMonth()
  }
}