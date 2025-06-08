package arcanegolem.yms.project

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import arcanegolem.yms.project.bottom_bar.YMSNavBar
import arcanegolem.yms.project.navigation.navigationGraph
import arcanegolem.yms.project.navigation.routes.Expenses
import arcanegolem.yms.project.top_bar.YMSTopAppBar

@Composable
fun YMSProjectRoot() {
  val navController = rememberNavController()

  Scaffold(
    topBar = { YMSTopAppBar(navController = navController) },
    bottomBar = { YMSNavBar(navController = navController) }
  ) { paddingValues ->
    NavHost(
      enterTransition = { EnterTransition.None },
      exitTransition = { ExitTransition.None },
      modifier = Modifier.padding(paddingValues),
      navController = navController,
      startDestination = Expenses
    ) { navigationGraph(navController) }
  }
}