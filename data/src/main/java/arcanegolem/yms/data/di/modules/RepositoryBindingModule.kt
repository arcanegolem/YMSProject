package arcanegolem.yms.data.di.modules

import arcanegolem.yms.data.repos.AccountRepositoryImpl
import arcanegolem.yms.data.repos.CategoriesRepositoryImpl
import arcanegolem.yms.data.repos.HistoryRepositoryImpl
import arcanegolem.yms.data.repos.TransactionsRepositoryImpl
import arcanegolem.yms.domain.repos.AccountRepository
import arcanegolem.yms.domain.repos.CategoriesRepository
import arcanegolem.yms.domain.repos.HistoryRepository
import arcanegolem.yms.domain.repos.TransactionsRepository
import dagger.Binds
import dagger.Module

@Module
internal interface RepositoryBindingModule {
  @Binds
  fun bindTransactionsRepositoryImplToTransactionsRepository(
    transactionsRepositoryImpl: TransactionsRepositoryImpl
  ) : TransactionsRepository

  @Binds
  fun bindCategoriesRepositoryImplToCategoriesRepository(
    categoriesRepositoryImpl: CategoriesRepositoryImpl
  ) : CategoriesRepository

  @Binds
  fun bindAccountRepositoryImplToAccountRepository(
    accountRepositoryImpl: AccountRepositoryImpl
  ) : AccountRepository

  @Binds
  fun bindHistoryRepositoryImplToHistoryRepository(
    historyRepositoryImpl: HistoryRepositoryImpl
  ) : HistoryRepository
}