package arcanegolem.yms.project.navigation

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import arcanegolem.yms.project.ui.screens.account.AccountScreenRoot
import arcanegolem.yms.project.ui.screens.categories.CategoriesScreenRoot
import arcanegolem.yms.project.ui.screens.expenses.ExpensesScreenRoot
import arcanegolem.yms.project.ui.screens.history.HistoryScreenRoot
import arcanegolem.yms.project.ui.screens.incomes.IncomesScreenRoot
import arcanegolem.yms.project.navigation.routes.Account
import arcanegolem.yms.project.navigation.routes.AccountEdit
import arcanegolem.yms.project.navigation.routes.ExpenseGroups
import arcanegolem.yms.project.navigation.routes.Expenses
import arcanegolem.yms.project.navigation.routes.History
import arcanegolem.yms.project.navigation.routes.Incomes
import arcanegolem.yms.project.navigation.routes.Settings
import arcanegolem.yms.project.ui.screens.account_edit.AccountEditScreenRoot
import arcanegolem.yms.project.ui.screens.settings.SettingsScreenRoot

fun NavGraphBuilder.navigationGraph(
  navController: NavController,
  viewModelProviderFactory : ViewModelProvider.Factory
) {
  composable<Expenses> {
    ExpensesScreenRoot(
      navController = navController,
      viewModel = viewModel(factory = viewModelProviderFactory)
    )
  }
  composable<Incomes> {
    IncomesScreenRoot(
      navController = navController,
      viewModel = viewModel(factory = viewModelProviderFactory)
    )
  }
  composable<Account> {
    AccountScreenRoot(
      navController = navController,
      viewModel = viewModel(factory = viewModelProviderFactory)
    )
  }
  composable<AccountEdit> { backStackEntry ->
    AccountEditScreenRoot(
      navController = navController,
      route = backStackEntry.toRoute(),
      viewModel = viewModel(factory = viewModelProviderFactory)
    )
  }
  composable<ExpenseGroups> {
    CategoriesScreenRoot(
      navController = navController,
      viewModel = viewModel(factory = viewModelProviderFactory)
    )
  }
  composable<Settings> {
    SettingsScreenRoot()
  }
  composable<History> { backStackEntry ->
    HistoryScreenRoot(
      navController = navController,
      route = backStackEntry.toRoute(),
      viewModel = viewModel(factory = viewModelProviderFactory)
    )
  }
}
