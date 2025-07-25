package arcanegolem.yms.transactions.ui.history.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import arcanegolem.yms.core.ui.R
import arcanegolem.yms.core.ui.components.YMSListItem
import arcanegolem.yms.core.utils.isEmoji
import arcanegolem.yms.core.utils.toFormattedDateTime
import arcanegolem.yms.core.domain.models.TransactionModel

/**
 * Версия [YMSListItem] для отображения транзакции с датой
 *
 * @param transactionModel модель транзакции [TransactionModel]
 * @param onClick колбек на клик по элементу
 */
@Composable
fun YMSDatedTransactionListItem(
  transactionModel : TransactionModel,
  onClick : (TransactionModel) -> Unit = {}
) {
  YMSListItem(
    modifier = Modifier
      .fillMaxWidth()
      .height(70.dp)
      .clickable { onClick(transactionModel) },
    lead = {
      if (transactionModel.emoji.isNotEmpty()){
        Box(
          modifier = Modifier.size(24.dp)
        ) {
          val isEmoji = remember(transactionModel.emoji) { transactionModel.emoji.isEmoji() }
          Box(
            modifier = Modifier
              .fillMaxSize()
              .clip(CircleShape)
              .background(MaterialTheme.colorScheme.secondary)
          )
          Text(
            modifier = Modifier.align(Alignment.Center),
            text = transactionModel.emoji,
            fontSize = if (isEmoji) 18.sp else 10.sp,
            lineHeight = if (isEmoji) 24.sp else 22.sp
          )
        }
      }
    },
    content = {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Column(
          modifier = Modifier.fillMaxWidth(0.5f)
        ) {
          Text(
            text = transactionModel.label,
            style = MaterialTheme.typography.bodyLarge,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            color = MaterialTheme.colorScheme.onSurface
          )
          if (transactionModel.comment?.isNotBlank() == true) {
            Text(
              text = transactionModel.comment ?: "",
              fontSize = 14.sp,
              lineHeight = 20.sp,
              maxLines = 1,
              overflow = TextOverflow.Ellipsis,
              color = MaterialTheme.colorScheme.onSurfaceVariant
            )
          }
        }
        Column {
          Text(
            modifier = Modifier.fillMaxWidth(),
            text = transactionModel.amountFormatted,
            fontSize = 16.sp,
            lineHeight = 24.sp,
            overflow = TextOverflow.Ellipsis,
            color = MaterialTheme.colorScheme.onSurface,
            textAlign = TextAlign.End
          )
          Text(
            modifier = Modifier.fillMaxWidth(),
            text = transactionModel.dateTimeMillis.toFormattedDateTime(),
            fontSize = 16.sp,
            lineHeight = 24.sp,
            overflow = TextOverflow.Ellipsis,
            color = MaterialTheme.colorScheme.onSurface,
            textAlign = TextAlign.End
          )
        }
      }
    },
    trail = {
      Icon(painter = painterResource(R.drawable.chevron_right), null)
    }
  )
}
