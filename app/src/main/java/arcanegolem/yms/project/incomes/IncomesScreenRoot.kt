package arcanegolem.yms.project.incomes

import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import arcanegolem.yms.project.R
import arcanegolem.yms.project.top_bar.ProvideYMSTopAppBarActions
import arcanegolem.yms.project.top_bar.ProvideYMSTopAppBarTitle

@Composable
fun IncomesScreenRoot() {
  ProvideYMSTopAppBarTitle { Text(text = stringResource(R.string.incomes_title)) }
  ProvideYMSTopAppBarActions { IconButton(onClick = {}) { Icon(painter = painterResource(R.drawable.history), contentDescription = null) } }

  IncomesScreen()
}