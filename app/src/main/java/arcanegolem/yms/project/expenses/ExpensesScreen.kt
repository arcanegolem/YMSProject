package arcanegolem.yms.project.expenses

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import arcanegolem.yms.project.R
import arcanegolem.yms.project.common.YMSFAB
import arcanegolem.yms.project.common.YMSListItem
import arcanegolem.yms.project.common.YMSTransactionResponseListItem
import arcanegolem.yms.project.mock_data.mockAccount
import arcanegolem.yms.project.mock_data.mockTransactions
import arcanegolem.yms.project.util.formatCash

@Composable
fun ExpensesScreen() {
  Box(modifier = Modifier.fillMaxSize()) {
    LazyColumn {
      item { 
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
              val totalAmountFormatted = mockTransactions
                .filter { !it.category.isIncome }
                .map { it.amount.toFloat() }
                .sum()
                .toString()
                .formatCash(mockAccount.currency)

              Text(
                text = stringResource(R.string.total_text),
                style = MaterialTheme.typography.bodyLarge,
                overflow = TextOverflow.Ellipsis,
                color = MaterialTheme.colorScheme.onSurface
              )

              Text(
                text = totalAmountFormatted,
                style = MaterialTheme.typography.bodyLarge,
                overflow = TextOverflow.Ellipsis,
                color = MaterialTheme.colorScheme.onSurface
              )
            }
          }
        )
      }
      items(mockTransactions.filter { !it.category.isIncome }, key = { it.toString() }) { transaction ->
        YMSTransactionResponseListItem(transaction)
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