package arcanegolem.yms.project.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import arcanegolem.yms.account.navigation.AccountDestinationModel
import arcanegolem.yms.categories.navigation.CategoriesDestinationModel
import arcanegolem.yms.core.ui.components.YMSConnectionDisplay
import arcanegolem.yms.core.ui.components.bottom_bar.YMSNavBar
import arcanegolem.yms.core.ui.components.top_bar.YMSTopAppBar
import arcanegolem.yms.core.utils.NetworkMonitor
import arcanegolem.yms.project.navigation.navigationGraph
import arcanegolem.yms.settings.navigation.SettingsDestinationModel
import arcanegolem.yms.transactions.navigation.Analysis
import arcanegolem.yms.transactions.navigation.ExpensesDestinationModel
import arcanegolem.yms.transactions.navigation.IncomesDestinationModel
import arcanegolem.yms.transactions.navigation.IncomesGraph

@Composable
fun YMSProjectRoot() {
  val navController = rememberNavController()
  val networkAvailable by NetworkMonitor.networkAvailable.collectAsStateWithLifecycle()
  val backStackEntry = navController.currentBackStackEntryAsState()

  val altTopAppBarColor = MaterialTheme.colorScheme.onSecondary
  val mainTopAppBarColor = MaterialTheme.colorScheme.primary

  val topAppBarColor by remember {
    derivedStateOf {
      if (backStackEntry.value?.destination?.hasRoute(Analysis::class) == true) {
        altTopAppBarColor
      } else {
        mainTopAppBarColor
      }
    }
  }

  Scaffold(
    topBar = {
      YMSTopAppBar(
        backStackEntry = backStackEntry.value,
        color = topAppBarColor
      )
    },
    bottomBar = {
      YMSNavBar(
        navController = navController,
        models = listOf(
          IncomesDestinationModel(),
          ExpensesDestinationModel(),
          AccountDestinationModel(),
          CategoriesDestinationModel(),
          SettingsDestinationModel()
        )
      )
    }
  ) { paddingValues ->
    Box(
      modifier = Modifier
        .fillMaxSize()
        .padding(paddingValues)
    ){
      NavHost(
        navController = navController,
        startDestination = IncomesGraph
      ) { navigationGraph(navController) }

      YMSConnectionDisplay(
        modifier = Modifier
          .align(Alignment.BottomCenter)
          .offset(y = -(16.dp)),
        networkAvailable = networkAvailable
      )
    }
  }
}
