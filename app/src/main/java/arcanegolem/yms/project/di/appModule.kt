package arcanegolem.yms.project.di

import arcanegolem.yms.project.account.AccountViewModel
import arcanegolem.yms.project.categories.CategoriesViewModel
import arcanegolem.yms.project.expenses.ExpensesViewModel
import arcanegolem.yms.project.incomes.IncomesViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
  viewModel { IncomesViewModel(get()) }
  viewModel { ExpensesViewModel(get()) }
  viewModel { AccountViewModel(get()) }
  viewModel { CategoriesViewModel(get()) }
}