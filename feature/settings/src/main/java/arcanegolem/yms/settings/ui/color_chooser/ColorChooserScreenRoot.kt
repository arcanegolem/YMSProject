package arcanegolem.yms.settings.ui.color_chooser

import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import arcanegolem.yms.core.ui.R
import arcanegolem.yms.core.ui.components.top_bar.ProvideYMSTopAppBarNavAction
import arcanegolem.yms.core.ui.components.top_bar.ProvideYMSTopAppBarTitle
import arcanegolem.yms.settings.di.resolveDaggerComponent

@Composable
fun ColorChooserScreenRoot(
  navController : NavController,
  viewModel : ColorChooserViewModel = viewModel(factory = resolveDaggerComponent().viewModelFactory())
) {
  ProvideYMSTopAppBarTitle {
    Text(text = stringResource(R.string.settings_p_color_text))
  }
  ProvideYMSTopAppBarNavAction {
    IconButton(
      onClick = { navController.popBackStack() }
    ) { Icon(painter = painterResource(R.drawable.arrow_back), contentDescription = null) }
  }
  
  val state = viewModel.state.collectAsStateWithLifecycle()
  
  ColorChooserScreen(state, viewModel::processEvent)
}