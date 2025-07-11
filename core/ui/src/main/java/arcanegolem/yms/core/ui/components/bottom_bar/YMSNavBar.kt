package arcanegolem.yms.core.ui.components.bottom_bar

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

/**
 * Кастомное нижнее навигационное меню на основе Material3 [NavigationBar]
 *
 * @param navController [NavController]
 */
@Composable
fun YMSNavBar(navController: NavController, models : List<YMSDestinationModel<Any>>) {
  val backStackEntry by navController.currentBackStackEntryAsState()
  val destination = backStackEntry?.destination

  NavigationBar {
    models.forEach { model ->
      val selected = destination?.parent?.hasRoute(model.destination::class)  == true

      NavigationBarItem(
        selected = selected,
        onClick = {
          if (!selected) {
            navController.popBackStack()
            navController.navigate(model.destination)
          }
        },
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
