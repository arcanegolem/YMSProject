package arcanegolem.yms.project.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.material.icons.rounded.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import arcanegolem.yms.project.R
import arcanegolem.yms.project.util.network.NetworkMonitor
import kotlinx.coroutines.delay

/**
 * Компонент отображения подключения к сети, предполагается использование в верхнеуровневом [Composable]
 */
@Composable
fun YMSConnectionDisplay(
  modifier: Modifier
) {
  val networkAvailable by NetworkMonitor.networkAvailable.collectAsStateWithLifecycle()
  var visible by remember(networkAvailable) { mutableStateOf(true) }

  LaunchedEffect(networkAvailable) {
    delay(1500)
    visible = !visible
  }

  AnimatedVisibility(
    modifier = modifier,
    visible = visible,
    enter = slideInVertically(initialOffsetY = { height -> -height }) + fadeIn(),
    exit = slideOutVertically(targetOffsetY = { height -> height }) + fadeOut()
  ) {
    Row (
      modifier = Modifier
        .clip(RoundedCornerShape(100))
        .background(color = if (networkAvailable) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.errorContainer)
        .padding(8.dp),
      verticalAlignment = Alignment.CenterVertically
    ) {
      Icon(
        imageVector = if (networkAvailable) Icons.Rounded.CheckCircle else Icons.Rounded.Warning,
        contentDescription = null,
        tint = if (networkAvailable) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onErrorContainer
      )
      Spacer(modifier = Modifier.width(4.dp))
      Text(
        text = stringResource(if (networkAvailable) R.string.connection_available_text else R.string.connection_unavailable_text),
        style = MaterialTheme.typography.bodyLarge,
        color = if (networkAvailable) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onErrorContainer
      )
    }
  }
}