package arcanegolem.yms.account.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import androidx.navigation.toRoute
import arcanegolem.yms.account.ui.account.AccountScreenRoot
import arcanegolem.yms.account.ui.account_edit.AccountEditScreenRoot

fun NavGraphBuilder.accountGraph(navController: NavController) {
  navigation<AccountGraph>(
    startDestination = Account
  ) {
    composable<Account> {
      AccountScreenRoot(navController = navController)
    }

    composable<AccountEdit> {
      AccountEditScreenRoot(
        navController = navController,
        route = it.toRoute()
      )
    }
  }
}