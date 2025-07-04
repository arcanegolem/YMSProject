package arcanegolem.yms.project.di

import arcanegolem.yms.domain.usecases.LoadAccountUseCase
import arcanegolem.yms.domain.usecases.LoadCategoriesUseCase
import arcanegolem.yms.domain.usecases.LoadExpensesUseCase
import arcanegolem.yms.domain.usecases.GetAccountUseCase
import arcanegolem.yms.domain.usecases.LoadAccountRemoteUseCase
import arcanegolem.yms.domain.usecases.LoadHistoryUseCase
import arcanegolem.yms.domain.usecases.LoadIncomesUseCase
import arcanegolem.yms.domain.usecases.UpdateAccountUseCase
import org.koin.dsl.module

val domainModule = module {
  factory { LoadIncomesUseCase(get()) }
  factory { LoadExpensesUseCase(get()) }
  factory { LoadAccountUseCase(get()) }
  factory { GetAccountUseCase(get()) }
  factory { LoadCategoriesUseCase(get()) }
  factory { LoadHistoryUseCase(get()) }
  factory { LoadAccountRemoteUseCase(get()) }
  factory { UpdateAccountUseCase(get()) }
}
