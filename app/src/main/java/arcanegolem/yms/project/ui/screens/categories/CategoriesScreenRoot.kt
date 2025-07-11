package arcanegolem.yms.project.ui.screens.categories

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import arcanegolem.yms.project.R
import arcanegolem.yms.project.ui.components.top_bar.ProvideYMSTopAppBarTitle

@Composable
fun CategoriesScreenRoot(
  navController: NavController,
  viewModel: CategoriesViewModel
) {
  ProvideYMSTopAppBarTitle { Text(text = stringResource(R.string.groups_title)) }

  val state = viewModel.state.collectAsStateWithLifecycle()

  CategoriesScreen(state, viewModel::processEvent)
}
