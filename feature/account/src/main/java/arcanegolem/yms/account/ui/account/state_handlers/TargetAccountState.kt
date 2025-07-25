package arcanegolem.yms.account.ui.account.state_handlers

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import arcanegolem.yms.account.ui.account.AccountState
import arcanegolem.yms.account.ui.account.components.DailyTransactionsViewer
import arcanegolem.yms.account.ui.account.helpers.toBarChartModel
import arcanegolem.yms.core.ui.R
import arcanegolem.yms.core.ui.components.YMSListItem
import arcanegolem.yms.graph.charts.bar.BarChart

@Composable
fun TargetAccountState(state : AccountState.Target) {
  var transactionsViewerShown by remember { mutableStateOf(false) }
  var selectedTransactionIndex by remember { mutableIntStateOf(0) }
  
  Box(modifier = Modifier.fillMaxSize()) {
    LazyColumn {
      item {
        YMSListItem(
          modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .background(MaterialTheme.colorScheme.secondary),
          lead = {
            Box(
              modifier = Modifier.size(24.dp)
            ) {
              Box(
                modifier = Modifier
                  .fillMaxSize()
                  .clip(CircleShape)
                  .background(MaterialTheme.colorScheme.onSecondary)
              )
              Text(
                modifier = Modifier.align(Alignment.Center),
                text = "\uD83D\uDCB0",
                fontSize = 18.sp,
                lineHeight = 24.sp
              )
            }
          },
          content = {
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween
            ) {
              Text(
                text = stringResource(R.string.balance_text),
                style = MaterialTheme.typography.bodyLarge,
                overflow = TextOverflow.Ellipsis,
                color = MaterialTheme.colorScheme.onSurface
              )

              Text(
                text = state.accountModel.balance,
                style = MaterialTheme.typography.bodyLarge,
                overflow = TextOverflow.Ellipsis,
                color = MaterialTheme.colorScheme.onSurface
              )
            }
          },
          trail = {
            Icon(painter = painterResource(R.drawable.chevron_right), null)
          }
        )
      }
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
              Text(
                text = stringResource(R.string.currency_text),
                style = MaterialTheme.typography.bodyLarge,
                overflow = TextOverflow.Ellipsis,
                color = MaterialTheme.colorScheme.onSurface
              )

              Text(
                text = state.accountModel.currency,
                style = MaterialTheme.typography.bodyLarge,
                overflow = TextOverflow.Ellipsis,
                color = MaterialTheme.colorScheme.onSurface
              )
            }
          },
          trail = {
            Icon(painter = painterResource(R.drawable.chevron_right), null)
          }
        )
      }
      item {
        val model = state.transactionsForMonthModel.toBarChartModel()
        
        Column(
          modifier = Modifier.fillMaxWidth(),
          horizontalAlignment = Alignment.CenterHorizontally
        ) {
          Spacer(modifier = Modifier.height(12.dp))
          Text(
            text = stringResource(R.string.changes_for_text)
                + " ${state.transactionsForMonthModel.month}.${state.transactionsForMonthModel.year}",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurface
          )
          BarChart(
            modifier = Modifier
              .padding(12.dp)
              .fillMaxWidth(),
            model = model,
            onBarClicked = { index ->
              selectedTransactionIndex = index
              transactionsViewerShown = true
            },
            barWidth = 4.dp
          )
        }
      }
    }
    
    DailyTransactionsViewer(
      isShown = transactionsViewerShown,
      onDismissRequest = { transactionsViewerShown = false },
      transactionsForDay = state.transactionsForMonthModel.transactionsByDay[selectedTransactionIndex]
    )
  }
}
