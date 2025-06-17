package arcanegolem.yms.project.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import arcanegolem.yms.project.account.AccountScreenRoot
import arcanegolem.yms.project.categories.CategoriesScreenRoot
import arcanegolem.yms.project.expenses.ExpensesScreenRoot
import arcanegolem.yms.project.history.HistoryScreenRoot
import arcanegolem.yms.project.incomes.IncomesScreenRoot
import arcanegolem.yms.project.navigation.routes.Account
import arcanegolem.yms.project.navigation.routes.ExpenseGroups
import arcanegolem.yms.project.navigation.routes.Expenses
import arcanegolem.yms.project.navigation.routes.History
import arcanegolem.yms.project.navigation.routes.Incomes
import arcanegolem.yms.project.navigation.routes.Settings
import arcanegolem.yms.project.settings.SettingsScreenRoot

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