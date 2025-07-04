package arcanegolem.yms.project.di

import arcanegolem.yms.project.ui.screens.account.AccountViewModel
import arcanegolem.yms.project.ui.screens.account_edit.AccountEditViewModel
import arcanegolem.yms.project.ui.screens.categories.CategoriesViewModel
import arcanegolem.yms.project.ui.screens.expenses.ExpensesViewModel
import arcanegolem.yms.project.ui.screens.history.HistoryViewModel
import arcanegolem.yms.project.ui.screens.incomes.IncomesViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
  viewModel { IncomesViewModel(get()) }
  viewModel { ExpensesViewModel(get()) }
  viewModel { AccountViewModel(get(), get()) }
  viewModel { CategoriesViewModel(get()) }
  viewModel { HistoryViewModel(get()) }
  viewModel { AccountEditViewModel(get()) }
}
