package arcanegolem.yms.categories.di

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext

@Composable
internal fun resolveDaggerComponent() : CategoriesComponent {
  val context = LocalContext.current.applicationContext
  val dependencies = remember {
    (context as CategoriesDependenciesProvider).resolveCategoriesDependencies()
  }
  return remember(dependencies) {
    DaggerCategoriesComponent.builder().categoriesDependencies(dependencies).build()
  }
}