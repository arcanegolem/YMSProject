package arcanegolem.yms.core.ui.components.time_picker

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import arcanegolem.yms.core.ui.R
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun YMSTimePicker(
  isActive : Boolean,
  onDismissRequest : () -> Unit,
  onTimeSelected : (PickedTime) -> Unit
) {
  val calendar = Calendar.getInstance()

  val timePickerState = rememberTimePickerState(
    initialHour = calendar.get(Calendar.HOUR_OF_DAY),
    initialMinute = calendar.get(Calendar.MINUTE),
    is24Hour = true
  )

  if (isActive) {
    Dialog(
      onDismissRequest = onDismissRequest
    ) {
      Column(modifier = Modifier.fillMaxWidth()) {
        Column(
          modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.onPrimaryContainer, RoundedCornerShape(8.dp))
            .padding(12.dp),
          horizontalAlignment = Alignment.CenterHorizontally
        ) {
          TimePicker(state = timePickerState)
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
          ) {
            Button(
              onClick = onDismissRequest
            ) { Text(text = stringResource(R.string.cancel_text)) }
            Button(
              onClick = {
                onTimeSelected(PickedTime(timePickerState.hour, timePickerState.minute))
                onDismissRequest()
              }
            ) { Text(text = stringResource(R.string.ok_text)) }
          }
        }
      }
    }
  }
}