package arcanegolem.yms.project.history.state_handlers

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import arcanegolem.yms.data.util.monthStartFormatted
import arcanegolem.yms.data.util.todayWithTimeFormatted
import arcanegolem.yms.project.R
import arcanegolem.yms.project.common.YMSListItem
import arcanegolem.yms.project.history.HistoryEvent
import arcanegolem.yms.project.history.HistoryState
import arcanegolem.yms.project.history.components.YMSDatedTransactionListItem

@Composable
fun TargetHistoryState(
  state : HistoryState.Target,
  eventProcessor : (HistoryEvent) -> Unit
) {
  Box(modifier = Modifier.fillMaxSize()) {
    Column {
      YMSListItem(
        modifier = Modifier
          .fillMaxWidth()
          .height(56.dp)
          .background(MaterialTheme.colorScheme.secondary),
        content = {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
          ) {
            Text(
              text = stringResource(R.string.start_text),
              style = MaterialTheme.typography.bodyLarge,
              overflow = TextOverflow.Ellipsis,
              color = MaterialTheme.colorScheme.onSurface
            )

            Text(
              text = monthStartFormatted(),
              style = MaterialTheme.typography.bodyLarge,
              overflow = TextOverflow.Ellipsis,
              color = MaterialTheme.colorScheme.onSurface
            )
          }
        }
      )
      YMSListItem(
        modifier = Modifier
          .fillMaxWidth()
          .height(56.dp)
          .background(MaterialTheme.colorScheme.secondary),
        content = {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
          ) {
            Text(
              text = stringResource(R.string.end_text),
              style = MaterialTheme.typography.bodyLarge,
              overflow = TextOverflow.Ellipsis,
              color = MaterialTheme.colorScheme.onSurface
            )

            Text(
              text = todayWithTimeFormatted(),
              style = MaterialTheme.typography.bodyLarge,
              overflow = TextOverflow.Ellipsis,
              color = MaterialTheme.colorScheme.onSurface
            )
          }
        }
      )
      YMSListItem(
        modifier = Modifier
          .fillMaxWidth()
          .height(56.dp)
          .background(MaterialTheme.colorScheme.secondary),
        content = {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
          ) {
            Text(
              text = stringResource(R.string.total_text),
              style = MaterialTheme.typography.bodyLarge,
              overflow = TextOverflow.Ellipsis,
              color = MaterialTheme.colorScheme.onSurface
            )

            Text(
              text = state.result.total,
              style = MaterialTheme.typography.bodyLarge,
              overflow = TextOverflow.Ellipsis,
              color = MaterialTheme.colorScheme.onSurface
            )
          }
        }
      )
      LazyColumn {
        items(state.result.transactions, key = { it.toString() }) { transaction ->
          YMSDatedTransactionListItem(transaction)
        }
      }
    }
  }
}