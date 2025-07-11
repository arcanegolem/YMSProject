package arcanegolem.yms.project.di

import arcanegolem.yms.account.data.repos.AccountRepositoryImpl
import arcanegolem.yms.account.domain.repos.AccountRepository
import arcanegolem.yms.categories.data.repos.CategoriesRepositoryImpl
import arcanegolem.yms.categories.domain.repos.CategoriesRepository
import arcanegolem.yms.core.data.datastore.DataStoreManager
import arcanegolem.yms.transactions.data.repos.TransactionsHistoryRepositoryImpl
import arcanegolem.yms.transactions.data.repos.TransactionsRepositoryImpl
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
    dataStoreManager : DataStoreManager
  ) : TransactionsRepository {
    return TransactionsRepositoryImpl(
      httpClient = httpClient,
      dataStoreManager = dataStoreManager
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
    dataStoreManager : DataStoreManager
  ) : AccountRepository {
    return AccountRepositoryImpl(
      httpClient = httpClient,
      dataStoreManager = dataStoreManager
    )
  }

  @[ApplicationScope Provides]
  fun provideCategoriesRepository(httpClient: HttpClient) : CategoriesRepository {
    return CategoriesRepositoryImpl(
      httpClient = httpClient
    )
  }
}