package arcanegolem.yms.project.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import arcanegolem.yms.project.ui.components.bottom_bar.YMSNavBar
import arcanegolem.yms.project.navigation.navigationGraph
import arcanegolem.yms.project.navigation.routes.Expenses
import arcanegolem.yms.project.ui.components.YMSConnectionDisplay
import arcanegolem.yms.project.ui.components.top_bar.YMSTopAppBar

@Composable
fun YMSProjectRoot() {
  val navController = rememberNavController()

  Scaffold(
    topBar = { YMSTopAppBar(navController = navController) },
    bottomBar = { YMSNavBar(navController = navController) }
  ) { paddingValues ->
    Box(
      modifier = Modifier
        .fillMaxSize()
        .padding(paddingValues)
    ){
      NavHost(
        navController = navController,
        startDestination = Expenses
      ) { navigationGraph(navController) }

      YMSConnectionDisplay(
        modifier = Modifier
          .align(Alignment.BottomCenter)
          .offset(y = -(16.dp))
      )
    }
  }
}