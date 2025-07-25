package arcanegolem.yms.settings.ui.settings.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import arcanegolem.yms.core.ui.R
import arcanegolem.yms.core.ui.components.YMSListItem
import arcanegolem.yms.core.utils.toFormattedDateTime
import kotlinx.coroutines.launch
import java.io.File

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppInfoSheet(
  isShown : Boolean,
  appVersion : String,
  onDismissRequest : () -> Unit
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
  
  val context = LocalContext.current
  val updateDate = remember(context) {
    val appInfo = context.packageManager.getApplicationInfo(context.packageName, 0)
    val appFile = appInfo.sourceDir
    val timeMillis = File(appFile).lastModified()
    
    timeMillis.toFormattedDateTime()
  }
  
  if (isShown) {
    ModalBottomSheet(
      sheetState = state,
      onDismissRequest = onDismissLocal
    ) {
      Column(
        modifier = Modifier.fillMaxWidth()
      ) {
        YMSListItem(
          modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
          content = {
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween,
              verticalAlignment = Alignment.CenterVertically
            ) {
              Text(
                text = stringResource(R.string.version_text),
                style = MaterialTheme.typography.bodyLarge
              )
              
              Text(
                text = appVersion,
                style = MaterialTheme.typography.bodyLarge
              )
            }
          }
        )
        YMSListItem(
          modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
          content = {
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween,
              verticalAlignment = Alignment.CenterVertically
            ) {
              Text(
                text = stringResource(R.string.last_updated_text),
                style = MaterialTheme.typography.bodyLarge
              )
              
              Text(
                text = updateDate,
                style = MaterialTheme.typography.bodyLarge
              )
            }
          }
        )
      }
    }
  }
}