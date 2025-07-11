package arcanegolem.yms.transactions.ui.expenses.state_handlers

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import arcanegolem.yms.core.ui.R
import arcanegolem.yms.core.ui.components.YMSFAB
import arcanegolem.yms.core.ui.components.YMSListItem
import arcanegolem.yms.transactions.ui.components.YMSTransactionListItem
import arcanegolem.yms.transactions.ui.expenses.ExpensesState

@Composable
fun TargetExpensesState(state : ExpensesState.Target) {
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
          YMSTransactionListItem(transaction)
        }
      }
    }

    YMSFAB(
      modifier = Modifier
        .align(Alignment.BottomEnd)
        .offset((-14).dp, (-16).dp),
      onClick = {}
    ) {
      Icon(Icons.Rounded.Add, null)
    }
  }
}
