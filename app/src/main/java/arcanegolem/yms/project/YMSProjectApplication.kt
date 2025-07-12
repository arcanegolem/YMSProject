package arcanegolem.yms.project

import android.app.Application
import android.content.Context
import arcanegolem.yms.account.di.AccountDependencies
import arcanegolem.yms.account.di.AccountDependenciesProvider
import arcanegolem.yms.categories.di.CategoriesDependencies
import arcanegolem.yms.categories.di.CategoriesDependenciesProvider
import arcanegolem.yms.project.di.ApplicationComponent
import arcanegolem.yms.project.di.DaggerApplicationComponent
import arcanegolem.yms.transactions.di.TransactionsDependencies
import arcanegolem.yms.transactions.di.TransactionsDependenciesProvider

class YMSProjectApplication
  : TransactionsDependenciesProvider,
  CategoriesDependenciesProvider,
  AccountDependenciesProvider,
  Application() {

  val applicationComponent : ApplicationComponent by lazy {
    DaggerApplicationComponent.factory()
      .create(this, BuildConfig.TOKEN)
  }

  override fun resolveTransactionsDependencies(): TransactionsDependencies {
    return applicationComponent
  }

  override fun resolveCategoriesDependencies(): CategoriesDependencies {
    return applicationComponent
  }

  override fun resolveAccountDependencies(): AccountDependencies {
    return applicationComponent
  }
}

val Context.applicationComponent : ApplicationComponent
  get() = when(this) {
    is YMSProjectApplication -> applicationComponent
    else -> this.applicationContext.applicationComponent
  }
