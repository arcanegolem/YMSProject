package arcanegolem.yms.project.di

import android.content.Context
import arcanegolem.yms.account.di.AccountDependencies
import arcanegolem.yms.account.domain.repos.AccountRepository
import arcanegolem.yms.categories.di.CategoriesDependencies
import arcanegolem.yms.categories.domain.repos.CategoriesRepository
import arcanegolem.yms.project.di.worker.DaggerWorkerFactory
import arcanegolem.yms.project.di.worker.WorkerBindingModule
import arcanegolem.yms.settings.di.SettingsDependencies
import arcanegolem.yms.settings.domain.repos.SettingsRepository
import arcanegolem.yms.transactions.di.TransactionsDependencies
import arcanegolem.yms.transactions.domain.repos.TransactionsAnalysisRepository
import arcanegolem.yms.transactions.domain.repos.TransactionsHistoryRepository
import arcanegolem.yms.transactions.domain.repos.TransactionsRepository
import dagger.BindsInstance
import dagger.Component

@ApplicationScope
@Component(
  modules = [
    RepositoryModule::class,
    StorageModule::class,
    NetworkModule::class,
    WorkerBindingModule::class
  ]
)
interface ApplicationComponent
  : TransactionsDependencies, AccountDependencies, CategoriesDependencies, SettingsDependencies {

  override fun resolveAccountRepository(): AccountRepository
  override fun resolveCategoriesRepository(): CategoriesRepository
  override fun resolveTransactionsRepository(): TransactionsRepository
  override fun resolveTransactionsHistoryRepository(): TransactionsHistoryRepository
  override fun resolveTransactionsAnalysisRepository(): TransactionsAnalysisRepository
  override fun resolveSettingsRepository(): SettingsRepository

  fun workerFactory() : DaggerWorkerFactory

  @Component.Factory
  interface Factory {
    fun create(
      @BindsInstance context : Context,
      @BindsInstance @TokenQualifier token : String
    ) : ApplicationComponent
  }
}