package arcanegolem.yms.account.ui.account.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import arcanegolem.yms.account.domain.models.TransactionsForDay
import arcanegolem.yms.core.ui.R
import arcanegolem.yms.core.ui.components.YMSTransactionListItem
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DailyTransactionsViewer(
  isShown : Boolean,
  onDismissRequest : () -> Unit,
  transactionsForDay : TransactionsForDay
) {
  val state = rememberModalBottomSheetState(skipPartiallyExpanded = true)
  val sheetScope = rememberCoroutineScope()
  
  val onDismissLocal : () -> Unit = {
    sheetScope.launch {
      state.hide()
    }.invokeOnCompletion {
      onDismissRequest()
    }
  }
  
  if (isShown) {
    ModalBottomSheet(
      sheetState = state,
      onDismissRequest = onDismissLocal
    ) {
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .fillMaxHeight(0.6f),
        horizontalAlignment = Alignment.CenterHorizontally
      ) {
        Text(
          text = transactionsForDay.dayOfMonth +
              " ${stringResource(R.string.day_text)}, " +
              "${stringResource(R.string.total_text)}: " +
              transactionsForDay.dayTotal,
          style = MaterialTheme.typography.bodyLarge,
          color = MaterialTheme.colorScheme.onSurface
        )
        Spacer(modifier = Modifier.height(8.dp))
        LazyColumn(
          modifier = Modifier.fillMaxWidth()
        ) {
          item {
            HorizontalDivider(thickness = 1.dp)
          }
          items(items = transactionsForDay.transactions, key = { it.toString() }) {
            YMSTransactionListItem(transactionModel = it)
          }
        }
      }
    }
  }
}