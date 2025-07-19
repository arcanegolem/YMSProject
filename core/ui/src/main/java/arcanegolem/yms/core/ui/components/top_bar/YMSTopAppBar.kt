package arcanegolem.yms.core.ui.components.top_bar

import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavBackStackEntry

/**
 * Кастомный верхний бар на основе Material3 [CenterAlignedTopAppBar]
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun YMSTopAppBar(
  backStackEntry: NavBackStackEntry?,
  color: Color
) {
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
      containerColor = color
    )
  )
}
