package arcanegolem.yms.project.di

import arcanegolem.yms.data.repos.AccountRepositoryMockImpl
import arcanegolem.yms.data.repos.CategoriesRepositoryMockImpl
import arcanegolem.yms.data.repos.TransactionsRepositoryMockImpl
import arcanegolem.yms.domain.repos.AccountRepository
import arcanegolem.yms.domain.repos.CategoriesRepository
import arcanegolem.yms.domain.repos.TransactionsRepository
import org.koin.dsl.module

val dataModule = module {
  single<TransactionsRepository> { TransactionsRepositoryMockImpl() }
  single<AccountRepository> { AccountRepositoryMockImpl() }
  single<CategoriesRepository> { CategoriesRepositoryMockImpl() }
}