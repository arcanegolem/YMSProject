package arcanegolem.yms.project.ui.screens.account_edit.state_handlers

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import arcanegolem.yms.project.R
import arcanegolem.yms.project.ui.components.YMSListItem
import arcanegolem.yms.project.ui.screens.account_edit.AccountEditEvent
import arcanegolem.yms.project.ui.screens.account_edit.AccountEditState
import arcanegolem.yms.project.ui.screens.account_edit.components.YMSTextFieldListItem
import arcanegolem.yms.project.ui.screens.account_edit.components.currency_chooser.CurrencyChooser
import arcanegolem.yms.project.util.visual_transformation.ThousandSeparator
import arcanegolem.yms.project.util.formatCashNoSymbol

@Composable
fun TargetAccountEditState(
  state : AccountEditState.Target,
  eventProcessor : (AccountEditEvent) -> Unit
) {
  val focusManager = LocalFocusManager.current

  val interactionSource = remember { MutableInteractionSource() }
  var isModalShown by remember { mutableStateOf(false) }

  Box(
    modifier = Modifier
      .fillMaxSize()
      .clickable(interactionSource, null) {
        focusManager.clearFocus()
      }
  ) {
    Column {
      YMSTextFieldListItem(
        focusManager = focusManager,
        descText = stringResource(R.string.account_name_text),
        text = state.result.name,
        keyboardType = KeyboardType.Text,
        onConsumeChanges = { eventProcessor(AccountEditEvent.UpdateAccountName(it)) }
      )
      YMSTextFieldListItem(
        focusManager = focusManager,
        descText = stringResource(R.string.balance_text),
        text = state.result.balance.formatCashNoSymbol(),
        keyboardType = KeyboardType.Text,
        onConsumeChanges = { eventProcessor(AccountEditEvent.UpdateAccountBalance(it)) },
        visualTransformation = VisualTransformation.ThousandSeparator
      )
      YMSListItem(
        modifier = Modifier
          .fillMaxWidth()
          .height(56.dp)
          .clickable { isModalShown = true },
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

    CurrencyChooser(
      isShown = isModalShown,
      onDismissRequest = { isModalShown = false },
      onCurrencySelected = { eventProcessor(AccountEditEvent.UpdateAccountCurrency(it)) }
    )
  }
}