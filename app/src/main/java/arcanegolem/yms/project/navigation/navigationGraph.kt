package arcanegolem.yms.project.navigation

import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import arcanegolem.yms.project.R
import arcanegolem.yms.project.navigation.routes.Account
import arcanegolem.yms.project.navigation.routes.ExpenseGroups
import arcanegolem.yms.project.navigation.routes.Expenses
import arcanegolem.yms.project.navigation.routes.Incomes
import arcanegolem.yms.project.navigation.routes.Settings
import arcanegolem.yms.project.top_bar.ProvideYMSTopAppBarActions
import arcanegolem.yms.project.top_bar.ProvideYMSTopAppBarTitle

fun NavGraphBuilder.navigationGraph(navController: NavController) {
  composable<Expenses> {
    ProvideYMSTopAppBarTitle { Text(text = stringResource(R.string.expenses_title)) }
    ProvideYMSTopAppBarActions { IconButton(onClick = {}) { Icon(painter = painterResource(R.drawable.history), contentDescription = null) } }

  }
  composable<Incomes> {
    ProvideYMSTopAppBarTitle { Text(text = stringResource(R.string.incomes_title)) }
    ProvideYMSTopAppBarActions { IconButton(onClick = {}) { Icon(painter = painterResource(R.drawable.history), contentDescription = null) } }

  }
  composable<Account> {
    ProvideYMSTopAppBarTitle { Text(text = stringResource(R.string.account_title)) }
    ProvideYMSTopAppBarActions { IconButton(onClick = {}) { Icon(painter = painterResource(R.drawable.edit), contentDescription = null) } }

  }
  composable<ExpenseGroups> {
    ProvideYMSTopAppBarTitle { Text(text = stringResource(R.string.groups_title)) }

  }
  composable<Settings> {
    ProvideYMSTopAppBarTitle { Text(text = stringResource(R.string.settings_title)) }

  }
}