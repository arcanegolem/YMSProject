package arcanegolem.yms.project.navigation

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

fun NavGraphBuilder.navigationGraph(navController: NavController) {
  composable<Expenses> {
    ExpensesScreenRoot(navController)
  }
  composable<Incomes> {
    IncomesScreenRoot(navController)
  }
  composable<Account> {
    AccountScreenRoot(navController)
  }
  composable<AccountEdit> { backStackEntry ->
    AccountEditScreenRoot(navController, backStackEntry.toRoute())
  }
  composable<ExpenseGroups> {
    CategoriesScreenRoot(navController)
  }
  composable<Settings> {
    SettingsScreenRoot()
  }
  composable<History> { backStackEntry ->
    HistoryScreenRoot(navController, backStackEntry.toRoute())
  }
}
