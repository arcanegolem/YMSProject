package arcanegolem.yms.transactions.di

import arcanegolem.yms.account.domain.repos.AccountRepository
import arcanegolem.yms.categories.domain.repos.CategoriesRepository
import arcanegolem.yms.transactions.domain.repos.TransactionsHistoryRepository
import arcanegolem.yms.transactions.domain.repos.TransactionsRepository

interface TransactionsDependencies {
  fun resolveTransactionsRepository() : TransactionsRepository
  fun resolveTransactionsHistoryRepository() : TransactionsHistoryRepository
  fun resolveAccountRepository() : AccountRepository
  fun resolveCategoriesRepository() : CategoriesRepository
}