package arcanegolem.yms.transactions.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import androidx.navigation.toRoute
import arcanegolem.yms.transactions.ui.expenses.ExpensesScreenRoot
import arcanegolem.yms.transactions.ui.history.HistoryScreenRoot
import arcanegolem.yms.transactions.ui.incomes.IncomesScreenRoot

fun NavGraphBuilder.transactionsGraph(
  navController: NavController
) {
  navigation<IncomesGraph>(
    startDestination = Incomes
  ) {
    composable<Incomes> {
      IncomesScreenRoot(navController = navController)
    }

    composable<History> {
      HistoryScreenRoot(
        navController = navController,
        route = it.toRoute()
      )
    }
  }

  navigation<ExpensesGraph>(
    startDestination = Expenses
  ) {
    composable<Expenses> {
      ExpensesScreenRoot(navController = navController)
    }

    composable<History> {
      HistoryScreenRoot(
        navController = navController,
        route = it.toRoute()
      )
    }
  }
}