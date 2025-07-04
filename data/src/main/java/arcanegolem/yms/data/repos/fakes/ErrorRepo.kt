package arcanegolem.yms.data.repos.fakes

import arcanegolem.yms.domain.models.AccountModel
import arcanegolem.yms.domain.models.CategoryModel
import arcanegolem.yms.domain.models.HistoryModel
import arcanegolem.yms.domain.models.TransactionsTotaledModel
import arcanegolem.yms.domain.repos.AccountRepository
import arcanegolem.yms.domain.repos.CategoriesRepository
import arcanegolem.yms.domain.repos.HistoryRepository
import arcanegolem.yms.domain.repos.TransactionsRepository
import kotlinx.coroutines.flow.Flow

/**
 * Имплементация всех репозиториев, где каждый метод кидает ошибку. Для тестирования стейта ошибки на любом экране
 */
internal class ErrorRepo : TransactionsRepository, CategoriesRepository, HistoryRepository, AccountRepository {
  override suspend fun loadExpenses(
    periodStartMillis: Long?,
    periodEndMillis: Long?
  ): TransactionsTotaledModel {
    throw RuntimeException("Error loading expenses!")
  }

  override suspend fun loadIncomes(
    periodStartMillis: Long?,
    periodEndMillis: Long?
  ): TransactionsTotaledModel {
    throw RuntimeException("Error loading incomes!")
  }

  override suspend fun loadCategories(): List<CategoryModel> {
    throw RuntimeException("Error loading categories!")
  }

  override suspend fun loadHistory(
    isIncome: Boolean,
    periodStartMillis: Long?,
    periodEndMillis: Long?
  ): HistoryModel {
    throw RuntimeException("Error loading history!")
  }

  override suspend fun loadAccount(accountId: Int): AccountModel {
    throw RuntimeException("Error loading account!")
  }

  override suspend fun loadFirstRemoteAccount() {
    throw RuntimeException("Error loading remote account!")
  }

  override suspend fun getAccount(): Flow<AccountModel?> {
    throw RuntimeException("Error loading account!")
  }

  override suspend fun updateAccount(model: AccountModel) {
    throw RuntimeException("Error updating account!")
  }
}
