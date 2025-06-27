package arcanegolem.yms.project.ui.components.bottom_bar

import android.annotation.SuppressLint
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.compose.currentBackStackEntryAsState
import arcanegolem.yms.project.R
import arcanegolem.yms.project.navigation.YMSDestinationModel
import arcanegolem.yms.project.navigation.routes.Account
import arcanegolem.yms.project.navigation.routes.ExpenseGroups
import arcanegolem.yms.project.navigation.routes.Expenses
import arcanegolem.yms.project.navigation.routes.Incomes
import arcanegolem.yms.project.navigation.routes.Settings

private val navbarModels = listOf(
  YMSDestinationModel(
    titleId = R.string.expenses_label,
    iconId = R.drawable.expense,
    destination = Expenses
  ),
  YMSDestinationModel(
    titleId = R.string.incomes_label,
    iconId = R.drawable.income,
    destination = Incomes
  ),
  YMSDestinationModel(
    titleId = R.string.account_label,
    iconId = R.drawable.cash_account,
    destination = Account
  ),
  YMSDestinationModel(
    titleId = R.string.groups_label,
    iconId = R.drawable.groups,
    destination = ExpenseGroups
  ),
  YMSDestinationModel(
    titleId = R.string.settings_label,
    iconId = R.drawable.settings,
    destination = Settings
  )
)

/**
 * Кастомное нижнее навигационное меню на основе Material3 [NavigationBar]
 *
 * @param navController [NavController]
 */
@SuppressLint("RestrictedApi")
@Composable
fun YMSNavBar(navController: NavController) {
  val backStackEntry by navController.currentBackStackEntryAsState()
  val destination = backStackEntry?.destination

  NavigationBar {
    navbarModels.forEach { model ->
      val selected = destination?.hasRoute(model.destination::class) == true

      NavigationBarItem(
        selected = selected,
        onClick = { navController.navigate(model.destination) },
        icon = {
          Icon(
            painter = painterResource(model.iconId),
            contentDescription = stringResource(model.titleId)
          )
        },
        label = { Text(text = stringResource(model.titleId)) },
        colors = NavigationBarItemDefaults.colors(
          selectedIconColor = MaterialTheme.colorScheme.primary,
          indicatorColor = MaterialTheme.colorScheme.secondary
        )
      )
    }
  }
}
