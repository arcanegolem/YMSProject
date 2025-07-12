package arcanegolem.yms.categories.ui.categories

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import arcanegolem.yms.categories.di.resolveDaggerComponent
import arcanegolem.yms.core.ui.R
import arcanegolem.yms.core.ui.components.top_bar.ProvideYMSTopAppBarTitle

@Composable
fun CategoriesScreenRoot(
  navController: NavController,
  viewModel : CategoriesViewModel = viewModel(factory = resolveDaggerComponent().viewModelFactory())
) {
  ProvideYMSTopAppBarTitle { Text(text = stringResource(R.string.groups_title)) }

  val state = viewModel.state.collectAsStateWithLifecycle()

  CategoriesScreen(state, viewModel::processEvent)
}
