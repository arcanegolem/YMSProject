package arcanegolem.yms.project.common.state_handlers.error

import android.content.ClipData
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ContentCopy
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ClipEntry
import androidx.compose.ui.platform.LocalClipboard
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import arcanegolem.yms.project.R
import kotlinx.coroutines.launch

@Composable
fun ErrorState(error : YMSError) {
  val clipboard = LocalClipboard.current
  val compositionScope = rememberCoroutineScope()
  val clipData = ClipData.newPlainText(stringResource(R.string.error_desc_text), error.throwable.toString())

  Box(modifier = Modifier.fillMaxSize()) {
    Column(
      modifier = Modifier.align(Alignment.Center),
      horizontalAlignment = Alignment.CenterHorizontally
    ) {
      Text(
        text = "${stringResource(error.description)}${error.throwable}",
        style = MaterialTheme.typography.bodyLarge,
        textAlign = TextAlign.Center
      )
      Spacer(modifier = Modifier.height(12.dp))
      Button(
        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary, contentColor = MaterialTheme.colorScheme.onPrimary),
        onClick = { compositionScope.launch { clipboard.setClipEntry(ClipEntry(clipData)) } }
      ) {
        Icon(
          imageVector = Icons.Rounded.ContentCopy,
          contentDescription = null
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
          text = stringResource(R.string.copy_error_desc_text),
          style = MaterialTheme.typography.bodyLarge
        )
      }
    }
  }
}