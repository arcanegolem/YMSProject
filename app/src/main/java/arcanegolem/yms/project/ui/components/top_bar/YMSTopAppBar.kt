package arcanegolem.yms.project.ui.components.top_bar

import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState

/**
 * Кастомный верхний бар на основе Material3 [CenterAlignedTopAppBar]
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun YMSTopAppBar(navController: NavController) {
  val backStackEntry by navController.currentBackStackEntryAsState()

  CenterAlignedTopAppBar(
    navigationIcon = {
      YMSTopAppBarNavAction(backStackEntry)
    },
    title = {
      YMSTopAppBarTitle(backStackEntry)
    },
    actions = {
      YMSTopAppBarActions(backStackEntry)
    },
    colors = TopAppBarDefaults.topAppBarColors(
      containerColor = MaterialTheme.colorScheme.primary
    )
  )
}