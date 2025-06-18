package arcanegolem.yms.project.history.components

import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import arcanegolem.yms.project.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun YMSDatePicker(
  isActive : Boolean,
  onDismissRequest : () -> Unit,
  onDateSelected : (Long?) -> Unit,
  initialDateMillis : Long? = null
) {
  val datePickerState = rememberDatePickerState()
  if (initialDateMillis != null) { datePickerState.displayedMonthMillis = initialDateMillis }

  if (isActive) {
    DatePickerDialog(
      onDismissRequest = onDismissRequest,
      confirmButton = {
        TextButton(
          onClick = {
            onDateSelected(datePickerState.selectedDateMillis)
            onDismissRequest()
          }
        ) {
          Text(text = stringResource(R.string.ok_text))
        }
      },
      dismissButton = {
        TextButton(
          onClick = {
            onDateSelected(null)
            onDismissRequest()
          }
        ) {
          Text(text = stringResource(R.string.cancel_text))
        }
      }
    ) {
      DatePicker(datePickerState)
    }
  }
}

enum class DatePickerSource {
  PERIOD_START,
  PERIOD_END
}