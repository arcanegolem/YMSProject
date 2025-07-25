package arcanegolem.yms.settings.ui.settings.components

import android.annotation.SuppressLint
import android.os.Vibrator
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.core.content.getSystemService
import arcanegolem.yms.core.ui.R
import arcanegolem.yms.core.ui.components.YMSListItem
import arcanegolem.yms.settings.ui.settings.models.hapticModels
import kotlinx.coroutines.launch

@SuppressLint("MissingPermission")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HapticSelector(
  isShown : Boolean,
  selectedPattern : LongArray,
  isHapticEnabled : Boolean,
  onDismissRequest : () -> Unit,
  onPatternSelected : (LongArray) -> Unit,
  onEnabledChange : (Boolean) -> Unit
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
  
  val vibrator = LocalContext.current.getSystemService<Vibrator>()
  
  if (isShown) {
    ModalBottomSheet(
      sheetState = state,
      onDismissRequest = onDismissLocal
    ) {
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .animateContentSize()
      ) {
        YMSListItem(
          modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
          content = {
            Text(
              modifier = Modifier.fillMaxWidth(),
              text = stringResource(R.string.settings_haptics_text),
              style = MaterialTheme.typography.bodyLarge
            )
          },
          trail = {
            Switch(
              checked = isHapticEnabled,
              onCheckedChange = onEnabledChange
            )
          }
        )
        if (isHapticEnabled){
          hapticModels.forEach { model ->
            YMSListItem(
              modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .clickable {
                  vibrator?.vibrate(model.pattern, -1)
                  onPatternSelected(model.pattern)
                },
              content = {
                Text(
                  modifier = Modifier.fillMaxWidth(),
                  text = stringResource(model.nameResId),
                  style = MaterialTheme.typography.bodyLarge,
                  overflow = TextOverflow.Ellipsis,
                  maxLines = 1,
                  color = MaterialTheme.colorScheme.onSurface
                )
              },
              trail = {
                RadioButton(
                  selected = model.pattern.contentEquals(selectedPattern),
                  onClick = {
                    vibrator?.vibrate(model.pattern, -1)
                    onPatternSelected(model.pattern)
                  }
                )
              }
            )
          }
        }
      }
    }
  }
}