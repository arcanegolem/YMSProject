package arcanegolem.yms.project.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import arcanegolem.yms.project.R
import arcanegolem.yms.project.models.TransactionResponse
import arcanegolem.yms.project.util.formatCash
import arcanegolem.yms.project.util.isAllowedText

@Composable
fun YMSTransactionResponseListItem(transactionResponse : TransactionResponse) {
  YMSListItem(
    modifier = Modifier
      .fillMaxWidth()
      .height(70.dp),
    lead = {
      if (transactionResponse.category.emoji.isNotEmpty()){
        Box(
          modifier = Modifier.size(24.dp)
        ) {
          val isEmoji =
            remember(transactionResponse.category.emoji) { !transactionResponse.category.emoji.isAllowedText() }
          Box(
            modifier = Modifier
              .fillMaxSize()
              .clip(CircleShape)
              .background(MaterialTheme.colorScheme.secondary)
          )
          Text(
            modifier = Modifier.align(Alignment.Center),
            text = transactionResponse.category.emoji,
            fontSize = if (isEmoji) 18.sp else 10.sp,
            lineHeight = if (isEmoji) 24.sp else 22.sp
          )
        }
      }
    },
    content = {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
      ) {
        Column {
          Text(
            text = transactionResponse.category.name,
            style = MaterialTheme.typography.bodyLarge,
            overflow = TextOverflow.Ellipsis,
            color = MaterialTheme.colorScheme.onSurface
          )
          transactionResponse.comment?.let {
            Text(
              text = it,
              fontSize = 14.sp,
              lineHeight = 20.sp,
              overflow = TextOverflow.Ellipsis,
              color = MaterialTheme.colorScheme.onSurfaceVariant
            )
          }
        }
        val amountFormatted = remember(transactionResponse.amount + transactionResponse.account.currency) {
          transactionResponse.amount.formatCash(transactionResponse.account.currency)
        }
        Text(
          text = amountFormatted,
          fontSize = 16.sp,
          lineHeight = 24.sp,
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