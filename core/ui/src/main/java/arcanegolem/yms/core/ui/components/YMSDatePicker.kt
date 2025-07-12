package arcanegolem.yms.core.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import arcanegolem.yms.core.ui.R

/**
 * Кастомный диалог с дейтпикером на основе Material3 [DatePickerDialog]
 *
 * @param isActive флаг отображения диалога
 * @param onDismissRequest колбек для закрытия диалога
 * @param onDateSelected колбек при подтверждении выбора даты, возвращает дату
 * в миллисекундах [Long], может быть null при отсутствии выбора
 * @param onDateClear колбек при нажатии кнопки очистки
 * @param initialDateMillis дата которая определяет изначальное положение календаря (год, месяц)
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun YMSDatePicker(
  isActive : Boolean,
  onDismissRequest : () -> Unit,
  onDateSelected : (Long?) -> Unit,
  onDateClear : () -> Unit,
  initialDateMillis : Long? = null
) {
  val datePickerState = rememberDatePickerState()
  if (initialDateMillis != null) { datePickerState.displayedMonthMillis = initialDateMillis }

  if (isActive) {
    DatePickerDialog(
      onDismissRequest = onDismissRequest,
      confirmButton = {
        Row {
          Spacer(modifier = Modifier.width(12.dp))
          TextButton(
            onClick = {
              onDateClear()
              onDismissRequest()
            },
            colors = ButtonDefaults.textButtonColors(
              contentColor = MaterialTheme.colorScheme.onPrimary,
            )
          ) {
            Text(text = stringResource(R.string.clear_text))
          }
          Spacer(modifier = Modifier.weight(1f))
          TextButton(
            onClick = onDismissRequest,
            colors = ButtonDefaults.textButtonColors(
              contentColor = MaterialTheme.colorScheme.onPrimary,
            )
          ) {
            Text(text = stringResource(R.string.cancel_text))
          }
          TextButton(
            onClick = {
              onDateSelected(datePickerState.selectedDateMillis)
              onDismissRequest()
            },
            colors = ButtonDefaults.textButtonColors(
              contentColor = MaterialTheme.colorScheme.onPrimary,
            )
          ) {
            Text(text = stringResource(R.string.ok_text))
          }
        }
      },
      colors = DatePickerDefaults.colors(
        containerColor = MaterialTheme.colorScheme.secondary
      )
    ) {
      DatePicker(
        state = datePickerState,
        showModeToggle = false,
        headline = null,
        title = null,
        colors = DatePickerDefaults.colors(
          containerColor = MaterialTheme.colorScheme.secondary,
          selectedDayContentColor = MaterialTheme.colorScheme.onPrimary,
          selectedYearContentColor = MaterialTheme.colorScheme.onPrimary,
          selectedDayContainerColor = MaterialTheme.colorScheme.primary,
          selectedYearContainerColor = MaterialTheme.colorScheme.primary,
          todayContentColor = MaterialTheme.colorScheme.primary,
          todayDateBorderColor = MaterialTheme.colorScheme.primary,
        )
      )
    }
  }
}