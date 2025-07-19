package arcanegolem.yms.project.di

import arcanegolem.yms.account.data.repos.AccountRepositoryImpl
import arcanegolem.yms.account.data.repos.SyncAccountRepositoryImpl
import arcanegolem.yms.account.domain.repos.AccountRepository
import arcanegolem.yms.account.domain.repos.SyncAccountRepository
import arcanegolem.yms.account.domain.usecases.LoadAccountRemoteUseCase
import arcanegolem.yms.categories.data.repos.CategoriesRepositoryImpl
import arcanegolem.yms.categories.data.repos.SyncCategoriesRepositoryImpl
import arcanegolem.yms.categories.domain.repos.CategoriesRepository
import arcanegolem.yms.categories.domain.repos.SyncCategoriesRepository
import arcanegolem.yms.core.data.database.YMSDatabase
import arcanegolem.yms.core.data.datastore.DataStoreManager
import arcanegolem.yms.settings.data.repos.SettingsRepositoryImpl
import arcanegolem.yms.settings.domain.repos.SettingsRepository
import arcanegolem.yms.transactions.data.repos.SyncTransactionsRepositoryImpl
import arcanegolem.yms.transactions.data.repos.TransactionAnalysisRepositoryImpl
import arcanegolem.yms.transactions.data.repos.TransactionsHistoryRepositoryImpl
import arcanegolem.yms.transactions.data.repos.TransactionsRepositoryImpl
import arcanegolem.yms.transactions.domain.repos.SyncTransactionsRepository
import arcanegolem.yms.transactions.domain.repos.TransactionsAnalysisRepository
import arcanegolem.yms.transactions.domain.repos.TransactionsHistoryRepository
import arcanegolem.yms.transactions.domain.repos.TransactionsRepository
import arcanegolem.yms.transactions.domain.usecases.LoadExpensesUseCase
import arcanegolem.yms.transactions.domain.usecases.LoadIncomesUseCase
import dagger.Module
import dagger.Provides
import io.ktor.client.HttpClient

@Module
class RepositoryModule {

  @[ApplicationScope Provides]
  fun provideTransactionsRepository(
    httpClient : HttpClient,
    dataStoreManager : DataStoreManager,
    loadAccountRemoteUseCase: LoadAccountRemoteUseCase,
    database : YMSDatabase
  ) : TransactionsRepository {
    return TransactionsRepositoryImpl(
      httpClient = httpClient,
      dataStoreManager = dataStoreManager,
      transactionsDao = database.transactionDao(),
      loadAccountRemoteUseCase = loadAccountRemoteUseCase,
      categoryDao = database.categoryDao(),
      queueDao = database.queueDao()
    )
  }

  @[ApplicationScope Provides]
  fun provideTransactionsHistoryRepository(
    loadIncomesUseCase: LoadIncomesUseCase,
    loadExpensesUseCase: LoadExpensesUseCase
  ) : TransactionsHistoryRepository {
    return TransactionsHistoryRepositoryImpl(
      loadIncomesUseCase = loadIncomesUseCase,
      loadExpensesUseCase = loadExpensesUseCase
    )
  }

  @[ApplicationScope Provides]
  fun provideAccountRepository(
    httpClient : HttpClient,
    dataStoreManager : DataStoreManager,
    database: YMSDatabase
  ) : AccountRepository {
    return AccountRepositoryImpl(
      httpClient = httpClient,
      dataStoreManager = dataStoreManager,
      queueDao = database.queueDao()
    )
  }

  @[ApplicationScope Provides]
  fun provideCategoriesRepository(
    httpClient: HttpClient,
    database: YMSDatabase
  ) : CategoriesRepository {
    return CategoriesRepositoryImpl(
      httpClient = httpClient,
      categoryDao = database.categoryDao()
    )
  }

  @[ApplicationScope Provides]
  fun provideSyncTransactionsRepository(
    httpClient: HttpClient,
    database: YMSDatabase
  ) : SyncTransactionsRepository {
    return SyncTransactionsRepositoryImpl(
      httpClient = httpClient,
      transactionsDao = database.transactionDao(),
      queueDao = database.queueDao(),
      categoryDao = database.categoryDao()
    )
  }

  @[ApplicationScope Provides]
  fun provideSyncAccountRepository(
    httpClient: HttpClient,
    dataStoreManager: DataStoreManager,
    database: YMSDatabase
  ) : SyncAccountRepository {
    return SyncAccountRepositoryImpl(
      httpClient = httpClient,
      dataStoreManager = dataStoreManager,
      queueDao = database.queueDao()
    )
  }

  @[ApplicationScope Provides]
  fun provideSyncCategoriesRepository(
    httpClient: HttpClient,
    database: YMSDatabase
  ) : SyncCategoriesRepository {
    return SyncCategoriesRepositoryImpl(
      httpClient = httpClient,
      categoryDao = database.categoryDao()
    )
  }

  @[ApplicationScope Provides]
  fun provideTransactionsAnalysisRepository(
    httpClient: HttpClient,
    dataStoreManager: DataStoreManager,
    loadAccountRemoteUseCase: LoadAccountRemoteUseCase,
    database: YMSDatabase
  ) : TransactionsAnalysisRepository {
    return TransactionAnalysisRepositoryImpl(
      httpClient = httpClient,
      dataStoreManager = dataStoreManager,
      transactionsDao = database.transactionDao(),
      loadAccountRemoteUseCase = loadAccountRemoteUseCase,
      categoryDao = database.categoryDao()
    )
  }

  @[ApplicationScope Provides]
  fun provideSettingsRepository(
    dataStoreManager: DataStoreManager
  ) : SettingsRepository {
    return SettingsRepositoryImpl(
      dataStoreManager = dataStoreManager
    )
  }
}