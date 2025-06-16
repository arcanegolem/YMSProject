package arcanegolem.yms.project.common

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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import arcanegolem.yms.project.R
import arcanegolem.yms.domain.models.TransactionModel
import arcanegolem.yms.project.util.isEmoji

@Composable
fun YMSTransactionListItem(
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

        ) {
          Text(
            text = transactionModel.label,
            style = MaterialTheme.typography.bodyLarge,
            overflow = TextOverflow.Ellipsis,
            color = MaterialTheme.colorScheme.onSurface
          )
          if (transactionModel.comment?.isNotBlank() == true) {
            transactionModel.comment?.let {
              Text(
                text = it,
                fontSize = 14.sp,
                lineHeight = 20.sp,
                overflow = TextOverflow.Ellipsis,
                color = MaterialTheme.colorScheme.onSurfaceVariant
              )
            }
          }
        }
        Text(
          text = transactionModel.amountFormatted,
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