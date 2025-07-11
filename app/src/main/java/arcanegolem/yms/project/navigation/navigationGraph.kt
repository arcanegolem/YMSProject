package arcanegolem.yms.project.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import arcanegolem.yms.account.navigation.accountGraph
import arcanegolem.yms.categories.navigation.categoriesGraph
import arcanegolem.yms.settings.navigation.settingsGraph
import arcanegolem.yms.transactions.navigation.transactionsGraph

fun NavGraphBuilder.navigationGraph(
  navController: NavController
) {
  transactionsGraph(navController)
  accountGraph(navController)
  categoriesGraph(navController)
  settingsGraph(navController)
}
