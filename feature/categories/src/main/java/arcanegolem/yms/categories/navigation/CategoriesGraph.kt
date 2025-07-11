package arcanegolem.yms.categories.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import arcanegolem.yms.categories.ui.categories.CategoriesScreenRoot

fun NavGraphBuilder.categoriesGraph(navController: NavController) {
  navigation<CategoriesGraph>(
    startDestination = Categories
  ) {
    composable<Categories> {
      CategoriesScreenRoot(
        navController = navController
      )
    }
  }
}