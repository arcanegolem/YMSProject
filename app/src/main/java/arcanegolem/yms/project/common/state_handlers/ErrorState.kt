package arcanegolem.yms.project.common.state_handlers

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import arcanegolem.yms.project.R

@Composable
fun ErrorState(error : Throwable) {
  Box(modifier = Modifier.fillMaxSize()) {
    Text(
      text = "${stringResource(R.string.error_state_msg)}${error.message}",
      style = MaterialTheme.typography.bodyLarge
    )
  }
}