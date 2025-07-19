package arcanegolem.yms.transactions.ui.history.state_handlers

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.intl.LocaleList
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hasRoute
import arcanegolem.yms.core.ui.R
import arcanegolem.yms.core.ui.components.YMSDatePicker
import arcanegolem.yms.core.ui.components.YMSListItem
import arcanegolem.yms.core.utils.toReadableDate
import arcanegolem.yms.transactions.navigation.IncomesGraph
import arcanegolem.yms.transactions.navigation.TransactionEditCreate
import arcanegolem.yms.transactions.ui.history.HistoryEvent
import arcanegolem.yms.transactions.ui.history.HistoryState
import arcanegolem.yms.transactions.ui.history.components.DatePickerSource
import arcanegolem.yms.transactions.ui.history.components.YMSDatedTransactionListItem

@Composable
fun TargetHistoryState(
  navController: NavController,
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
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Text(
              text = stringResource(R.string.start_text),
              style = MaterialTheme.typography.bodyLarge,
              overflow = TextOverflow.Ellipsis,
              color = MaterialTheme.colorScheme.onSurface
            )

            Text(
              text = state.result.periodStart.toReadableDate(LocaleList.current.localeList[0].language),
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
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Text(
              text = stringResource(R.string.end_text),
              style = MaterialTheme.typography.bodyLarge,
              overflow = TextOverflow.Ellipsis,
              color = MaterialTheme.colorScheme.onSurface
            )

            Text(
              text = state.result.periodEnd.toReadableDate(LocaleList.current.localeList[0].language),
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
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
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
          YMSDatedTransactionListItem(transaction) { t ->
            navController.navigate(TransactionEditCreate(
              transactionId = t.id,
              isIncome = navController.previousBackStackEntry?.destination?.parent?.hasRoute(IncomesGraph::class) == true,
              isArbitrary = t.isArbitrary
            ))
          }
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
