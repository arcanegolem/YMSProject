package arcanegolem.yms.project.di

import android.util.Log
import dagger.Module
import dagger.Provides
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

@Module
class NetworkModule {

  @Provides
  fun provideHttpClient(@TokenQualifier token : String) : HttpClient {
    return HttpClient(OkHttp) {
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
            BearerTokens(token, null)
          }
        }
      }
    }
  }
}