package arcanegolem.yms.data.di

import android.content.Context
import android.util.Log
import arcanegolem.yms.data.BuildConfig
import arcanegolem.yms.data.datastore.DataStoreManager
import arcanegolem.yms.data.datastore.preferencesDataStore
import arcanegolem.yms.data.repos.AccountRepositoryImpl
import arcanegolem.yms.data.repos.CategoriesRepositoryImpl
import arcanegolem.yms.data.repos.HistoryRepositoryImpl
import arcanegolem.yms.data.repos.TransactionsRepositoryImpl
import arcanegolem.yms.domain.repos.AccountRepository
import arcanegolem.yms.domain.repos.CategoriesRepository
import arcanegolem.yms.domain.repos.HistoryRepository
import arcanegolem.yms.domain.repos.TransactionsRepository
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.HttpRequestRetry
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.resources.Resources
import io.ktor.client.request.accept
import io.ktor.http.ContentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.dsl.module

val dataModule = module {
  single<CategoriesRepository> { CategoriesRepositoryImpl(get()) }
  single<TransactionsRepository> { TransactionsRepositoryImpl(get(), get()) }
  single<AccountRepository> { AccountRepositoryImpl(get(), get()) }
  single<HistoryRepository> { HistoryRepositoryImpl(get(), get()) }

  single<DataStoreManager> {
    val context = get<Context>()
    DataStoreManager(context.preferencesDataStore)
  }

  single {
    HttpClient(OkHttp) {
      install(Resources)
      //Установка перезапросов с интервалом в 2 секунды, 3 раза
      install(HttpRequestRetry) {
        retryOnServerErrors(maxRetries = 3)
        delayMillis { retry -> retry * 2000L }
      }

      install(ContentNegotiation) {
        json(Json { ignoreUnknownKeys = true })
      }

      defaultRequest {
        url("https://shmr-finance.ru/api/v1/")
        accept(ContentType("application", "json"))
      }

      install(Logging) {
        logger = object : Logger {
          override fun log(message: String) {
            Log.d("KtorHttpClient", message)
          }
        }
        level = LogLevel.ALL
      }

      // Токен берется из local.properties из поля TOKEN
      install(Auth) {
        bearer {
          loadTokens {
            BearerTokens(BuildConfig.TOKEN, null)
          }
        }
      }
    }
  }
}
