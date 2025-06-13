package arcanegolem.yms.project.categories

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import arcanegolem.yms.project.R
import arcanegolem.yms.project.top_bar.ProvideYMSTopAppBarTitle

@Composable
fun CategoriesScreenRoot() {
  ProvideYMSTopAppBarTitle { Text(text = stringResource(R.string.groups_title)) }

  CategoriesScreen()
}