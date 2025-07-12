package arcanegolem.yms.project.di

import android.content.Context
import arcanegolem.yms.account.di.AccountDependencies
import arcanegolem.yms.account.domain.repos.AccountRepository
import arcanegolem.yms.categories.di.CategoriesDependencies
import arcanegolem.yms.categories.domain.repos.CategoriesRepository
import arcanegolem.yms.transactions.di.TransactionsDependencies
import arcanegolem.yms.transactions.domain.repos.TransactionsHistoryRepository
import arcanegolem.yms.transactions.domain.repos.TransactionsRepository
import dagger.BindsInstance
import dagger.Component

@ApplicationScope
@Component(
  modules = [RepositoryModule::class, StorageModule::class, NetworkModule::class]
)
interface ApplicationComponent
  : TransactionsDependencies, AccountDependencies, CategoriesDependencies {

  override fun resolveAccountRepository(): AccountRepository
  override fun resolveCategoriesRepository(): CategoriesRepository
  override fun resolveTransactionsRepository(): TransactionsRepository
  override fun resolveTransactionsHistoryRepository(): TransactionsHistoryRepository

  @Component.Factory
  interface Factory {
    fun create(
      @BindsInstance context : Context,
      @BindsInstance @TokenQualifier token : String
    ) : ApplicationComponent
  }
}