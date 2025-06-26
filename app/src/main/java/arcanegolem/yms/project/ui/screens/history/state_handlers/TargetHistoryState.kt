package arcanegolem.yms.project.ui.screens.history.state_handlers

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import arcanegolem.yms.project.R
import arcanegolem.yms.project.ui.components.YMSListItem
import arcanegolem.yms.project.ui.screens.history.HistoryEvent
import arcanegolem.yms.project.ui.screens.history.HistoryState
import arcanegolem.yms.project.ui.components.DatePickerSource
import arcanegolem.yms.project.ui.components.YMSDatePicker
import arcanegolem.yms.project.ui.screens.history.components.YMSDatedTransactionListItem
import arcanegolem.yms.project.util.toReadableDate

@Composable
fun TargetHistoryState(
  state : HistoryState.Target,
  eventProcessor : (HistoryEvent) -> Unit
) {
  var datePickerActive by remember { mutableStateOf(false) }
  var datePickerSource by remember { mutableStateOf(DatePickerSource.PERIOD_START) }

  Box(modifier = Modifier.fillMaxSize()) {
    Column {
      YMSListItem(
        modifier = Modifier
          .fillMaxWidth()
          .height(56.dp)
          .background(MaterialTheme.colorScheme.secondary)
          .clickable {
            datePickerSource = DatePickerSource.PERIOD_START
            datePickerActive = true
          },
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
              text = state.result.periodStart.toReadableDate(),
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
          .background(MaterialTheme.colorScheme.secondary)
          .clickable {
            datePickerSource = DatePickerSource.PERIOD_END
            datePickerActive = true
          },
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
              text = state.result.periodEnd.toReadableDate(),
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
              text = state.result.transactionsTotaled.total,
              style = MaterialTheme.typography.bodyLarge,
              overflow = TextOverflow.Ellipsis,
              color = MaterialTheme.colorScheme.onSurface
            )
          }
        }
      )
      LazyColumn {
        items(state.result.transactionsTotaled.transactions, key = { it.toString() }) { transaction ->
          YMSDatedTransactionListItem(transaction)
        }
      }
    }

    YMSDatePicker(
      isActive = datePickerActive,
      onDismissRequest = { datePickerActive = false },
      onDateSelected = { selectedDate ->
        if (selectedDate != null){
          eventProcessor(HistoryEvent.ChangePeriodSideAndLoadData(datePickerSource, selectedDate))
        }
      },
      onDateClear = {
        eventProcessor(HistoryEvent.ChangePeriodSideAndLoadData(datePickerSource, null))
      },
      initialDateMillis = when (datePickerSource) {
        DatePickerSource.PERIOD_START -> state.result.periodStart
        DatePickerSource.PERIOD_END -> state.result.periodEnd
      }
    )
  }
}