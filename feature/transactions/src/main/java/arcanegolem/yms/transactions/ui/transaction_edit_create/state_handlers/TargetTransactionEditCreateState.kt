package arcanegolem.yms.transactions.ui.transaction_edit_create.state_handlers

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import arcanegolem.yms.core.ui.R
import arcanegolem.yms.core.ui.components.YMSDatePicker
import arcanegolem.yms.core.ui.components.YMSListItem
import arcanegolem.yms.core.ui.components.YMSTextFieldListItem
import arcanegolem.yms.core.ui.components.time_picker.YMSTimePicker
import arcanegolem.yms.core.ui.components.utils.ThousandSeparator
import arcanegolem.yms.transactions.ui.transaction_edit_create.TransactionEditCreateEvent
import arcanegolem.yms.transactions.ui.transaction_edit_create.TransactionEditCreateState
import arcanegolem.yms.transactions.ui.transaction_edit_create.components.CategoryChooser
import arcanegolem.yms.transactions.ui.transaction_edit_create.components.TransactionSyncErrorDialog

@Composable
fun TargetTransactionEditCreateState(
  state : TransactionEditCreateState.Target,
  eventProcessor : (TransactionEditCreateEvent) -> Unit,
  onScreenExit : () -> Unit,
  isEdit : Boolean
) {
  val focusManager = LocalFocusManager.current

  val interactionSource = remember { MutableInteractionSource() }

  var categoryChooserShown by rememberSaveable { mutableStateOf(false) }
  var datePickerShown by rememberSaveable { mutableStateOf(false) }
  var timePickerShown by rememberSaveable { mutableStateOf(false) }
  var showTransactionSyncErrorDialog by remember(
    "${state.sameErrorCounter}${state.transactionSyncError}"
  ) { mutableStateOf(state.transactionSyncError != null) }

  Box(
    modifier = Modifier
      .fillMaxWidth()
      .clickable(interactionSource, null) {
        focusManager.clearFocus()
      }
  ) {
    LazyColumn {
      item {
        YMSListItem(
          modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
          content = {
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween
            ) {
              Text(
                text = stringResource(R.string.account_text),
                style = MaterialTheme.typography.bodyLarge,
                overflow = TextOverflow.Ellipsis,
                color = MaterialTheme.colorScheme.onSurface
              )

              Text(
                text = state.result.accountName,
                style = MaterialTheme.typography.bodyLarge,
                overflow = TextOverflow.Ellipsis,
                color = MaterialTheme.colorScheme.onSurface
              )
            }
          }
        )
        YMSListItem(
          modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .clickable { categoryChooserShown = true },
          content = {
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween
            ) {
              Text(
                text = stringResource(R.string.category_text),
                style = MaterialTheme.typography.bodyLarge,
                overflow = TextOverflow.Ellipsis,
                color = MaterialTheme.colorScheme.onSurface
              )

              Text(
                text = state.result.categoryName.ifEmpty { stringResource(R.string.choose_text) },
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
        YMSListItem(
          modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .clickable { datePickerShown = true },
          content = {
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween
            ) {
              Text(
                text = stringResource(R.string.date_text),
                style = MaterialTheme.typography.bodyLarge,
                overflow = TextOverflow.Ellipsis,
                color = MaterialTheme.colorScheme.onSurface
              )

              Text(
                text = state.result.date.ifEmpty { stringResource(R.string.choose_text) },
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
        YMSListItem(
          modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .clickable { timePickerShown = true },
          content = {
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween
            ) {
              Text(
                text = stringResource(R.string.time_text),
                style = MaterialTheme.typography.bodyLarge,
                overflow = TextOverflow.Ellipsis,
                color = MaterialTheme.colorScheme.onSurface
              )

              Text(
                text = state.result.time.ifEmpty { stringResource(R.string.choose_text) },
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
        YMSTextFieldListItem(
          focusManager = focusManager,
          descText = stringResource(R.string.amount_text),
          text = state.result.amount,
          keyboardType = KeyboardType.Number,
          onConsumeChanges = { eventProcessor(TransactionEditCreateEvent.UpdateTransactionAmount(it)) },
          visualTransformation = VisualTransformation.ThousandSeparator
        )
        YMSTextFieldListItem(
          focusManager = focusManager,
          descText = stringResource(R.string.comment_text),
          text = state.result.comment,
          keyboardType = KeyboardType.Text,
          onConsumeChanges = { eventProcessor(TransactionEditCreateEvent.UpdateTransactionComment(it)) }
        )

        if (isEdit) {
          Spacer(modifier = Modifier.height(32.dp))
          Button(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
            onClick = { eventProcessor(TransactionEditCreateEvent.DeleteTransaction{ onScreenExit() }) },
            colors = ButtonDefaults.buttonColors(
              containerColor = MaterialTheme.colorScheme.errorContainer,
              contentColor = MaterialTheme.colorScheme.onErrorContainer
            )
          ) {
            Text(
              text = stringResource(R.string.delete_text),
              style = MaterialTheme.typography.labelLarge
            )
          }
        }
      }
    }

    YMSDatePicker(
      isActive = datePickerShown,
      onDismissRequest = { datePickerShown = false },
      onDateSelected = { dateMillis ->
        eventProcessor(TransactionEditCreateEvent.UpdateTransactionDate(dateMillis))
      },
      onDateClear = {
        eventProcessor(TransactionEditCreateEvent.UpdateTransactionDate(null))
      },
      initialDateMillis = System.currentTimeMillis()
    )

    YMSTimePicker(
      isActive = timePickerShown,
      onDismissRequest = { timePickerShown = false },
      onTimeSelected = {
        eventProcessor(TransactionEditCreateEvent.UpdateTransactionTime(it))
      }
    )

    CategoryChooser(
      isShown = categoryChooserShown,
      onDismissRequest = { categoryChooserShown = false },
      onCategorySelected = { category ->
        eventProcessor(TransactionEditCreateEvent.UpdateTransactionCategory(category.id,category.name))
      },
      models = state.availableCategories,
    )

    TransactionSyncErrorDialog(
      isShown = showTransactionSyncErrorDialog,
      onRetryClicked = { eventProcessor(TransactionEditCreateEvent.ConsumeUpdates{ onScreenExit() } ) },
      onDismissRequest = { showTransactionSyncErrorDialog = false }
    )
  }
}