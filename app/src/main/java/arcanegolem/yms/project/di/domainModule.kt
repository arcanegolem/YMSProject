package arcanegolem.yms.project.di

import arcanegolem.yms.domain.usecases.LoadAccountUseCase
import arcanegolem.yms.domain.usecases.LoadCategoriesUseCase
import arcanegolem.yms.domain.usecases.LoadExpensesUseCase
import arcanegolem.yms.domain.usecases.LoadFirstAccountUseCase
import arcanegolem.yms.domain.usecases.LoadHistoryUseCase
import arcanegolem.yms.domain.usecases.LoadIncomesUseCase
import org.koin.dsl.module

val domainModule = module {
  factory { LoadIncomesUseCase(get()) }
  factory { LoadExpensesUseCase(get()) }
  factory { LoadAccountUseCase(get()) }
  factory { LoadFirstAccountUseCase(get()) }
  factory { LoadCategoriesUseCase(get()) }
  factory { LoadHistoryUseCase(get()) }
}
