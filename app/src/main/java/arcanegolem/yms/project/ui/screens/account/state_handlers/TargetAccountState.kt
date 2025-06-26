package arcanegolem.yms.project.ui.screens.account.state_handlers

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import arcanegolem.yms.project.R
import arcanegolem.yms.project.ui.screens.account.AccountState
import arcanegolem.yms.project.ui.components.YMSFAB
import arcanegolem.yms.project.ui.components.YMSListItem

@Composable
fun TargetAccountState(state : AccountState.Target) {
  Box(modifier = Modifier.fillMaxSize()) {
    LazyColumn {
      item {
        YMSListItem(
          modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .background(MaterialTheme.colorScheme.secondary)
            .clickable {  },
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
                text = state.result.balance,
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
            .background(MaterialTheme.colorScheme.secondary)
            .clickable {  },
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
                text = state.result.currency,
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