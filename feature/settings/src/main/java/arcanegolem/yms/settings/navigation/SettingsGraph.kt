package arcanegolem.yms.settings.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import arcanegolem.yms.settings.ui.color_chooser.ColorChooserScreenRoot
import arcanegolem.yms.settings.ui.settings.SettingsScreenRoot

fun NavGraphBuilder.settingsGraph(
  navController: NavController
) {
  navigation<SettingsGraph>(
    startDestination = Settings
  ) {
    composable<Settings> {
      SettingsScreenRoot(
        navController = navController
      )
    }
    
    composable<PrimaryColorChooser> {
      ColorChooserScreenRoot(
        navController = navController
      )
    }
  }
}