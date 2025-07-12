package arcanegolem.yms.transactions.ui.transaction_edit_create.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import arcanegolem.yms.core.ui.R

@Composable
fun TransactionSyncErrorDialog(
  isShown : Boolean,
  onRetryClicked : () -> Unit,
  onDismissRequest : () -> Unit
) {
  if (isShown) {
    Dialog(
      onDismissRequest = onDismissRequest
    ) {
      Column {
        Column(
          modifier = Modifier
            .background(MaterialTheme.colorScheme.onPrimaryContainer, RoundedCornerShape(8.dp))
            .padding(12.dp),
          horizontalAlignment = Alignment.CenterHorizontally
        ) {
          Text(
            text = stringResource(R.string.something_went_wrong),
            style = MaterialTheme.typography.bodyLarge
          )
          Spacer(modifier = Modifier.height(12.dp))
          Button(
            onClick = onRetryClicked
          ) {
            Text(
              text = stringResource(R.string.retry_text)
            )
          }
        }
      }
    }
  }
}