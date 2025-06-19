package arcanegolem.yms.data.di.fakes

import arcanegolem.yms.data.repos.fakes.ErrorRepo
import arcanegolem.yms.domain.repos.AccountRepository
import arcanegolem.yms.domain.repos.CategoriesRepository
import arcanegolem.yms.domain.repos.HistoryRepository
import arcanegolem.yms.domain.repos.TransactionsRepository
import org.koin.dsl.module

val fakeDataModule = module {
  single<CategoriesRepository> { ErrorRepo() }
  single<TransactionsRepository> { ErrorRepo() }
  single<AccountRepository> { ErrorRepo() }
  single<HistoryRepository> { ErrorRepo() }
}