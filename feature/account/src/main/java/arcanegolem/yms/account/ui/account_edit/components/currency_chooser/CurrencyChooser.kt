package arcanegolem.yms.account.ui.account_edit.components.currency_chooser

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import arcanegolem.yms.core.ui.R
import arcanegolem.yms.core.ui.components.YMSListItem
import kotlinx.coroutines.launch

private val currencyModels = listOf(
  CurrencyModel(
    iconId = R.drawable.ruble,
    stringRepresentationId = R.string.ruble_text,
    symbol = "₽"
  ),
  CurrencyModel(
    iconId = R.drawable.dollar,
    stringRepresentationId = R.string.dollar_text,
    symbol = "$"
  ),
  CurrencyModel(
    iconId = R.drawable.euro,
    stringRepresentationId = R.string.euro_text,
    symbol = "€"
  )
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CurrencyChooser(
  isShown : Boolean,
  onDismissRequest : () -> Unit,
  onCurrencySelected : (String) -> Unit
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
      ) {
        currencyModels.forEach { model ->
          YMSListItem(
            modifier = Modifier
              .fillMaxWidth()
              .height(72.dp)
              .clickable {
                onCurrencySelected(model.symbol)
                onDismissLocal()
              },
            lead = {
              Icon(
                modifier = Modifier.size(24.dp),
                painter = painterResource(model.iconId),
                contentDescription = model.symbol
              )
            },
            content = {
              Text(
                text = stringResource(model.stringRepresentationId),
                style = MaterialTheme.typography.bodyLarge,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
                color = MaterialTheme.colorScheme.onSurface
              )
            }
          )
        }
        YMSListItem(
          modifier = Modifier
            .fillMaxWidth()
            .height(72.dp)
            .background(MaterialTheme.colorScheme.errorContainer)
            .clickable { onDismissLocal() },
          lead = {
            Icon(
              modifier = Modifier.size(24.dp),
              painter = painterResource(R.drawable.cancel_circle),
              contentDescription = null,
              tint = MaterialTheme.colorScheme.onErrorContainer
            )
          },
          content = {
            Text(
              text = stringResource(R.string.cancel_text),
              style = MaterialTheme.typography.bodyLarge,
              overflow = TextOverflow.Ellipsis,
              maxLines = 1,
              color = MaterialTheme.colorScheme.onErrorContainer
            )
          }
        )
      }
    }

    LaunchedEffect(Unit) {
      sheetScope.launch {
        state.expand()
      }
    }
  }
}