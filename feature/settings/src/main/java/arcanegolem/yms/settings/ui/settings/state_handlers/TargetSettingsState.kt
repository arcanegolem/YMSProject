package arcanegolem.yms.settings.ui.settings.state_handlers

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import arcanegolem.yms.core.ui.R
import arcanegolem.yms.core.ui.components.YMSListItem
import arcanegolem.yms.settings.ui.settings.SettingsState


/**
 * Тут пока немного уродливо, просто сделал те же поля что и в Figma
 */
@Composable
fun TargetSettingsState(
  state : SettingsState.Target
) {
  LazyColumn {
    item {
      var checked by remember { mutableStateOf(false) }

      YMSListItem(
        modifier = Modifier
          .fillMaxWidth()
          .height(56.dp),
        content = {
          Text(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(R.string.settings_theme_text),
            style = MaterialTheme.typography.bodyLarge
          )
        },
        trail = { Switch(checked = checked, onCheckedChange = { checked = it }) }
      )
    }
    item {
      YMSListItem(
        modifier = Modifier
          .fillMaxWidth()
          .height(56.dp)
          .clickable { },
        content = {
          Text(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(R.string.settings_p_color_text),
            style = MaterialTheme.typography.bodyLarge
          )
        },
        trail = { Icon(painter = painterResource(R.drawable.arrow_right), null) }
      )
    }
    item {
      YMSListItem(
        modifier = Modifier
          .fillMaxWidth()
          .height(56.dp)
          .clickable { },
        content = {
          Text(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(R.string.settings_sounds_text),
            style = MaterialTheme.typography.bodyLarge
          )
        },
        trail = { Icon(painter = painterResource(R.drawable.arrow_right), null) }
      )
    }
    item {
      YMSListItem(
        modifier = Modifier
          .fillMaxWidth()
          .height(56.dp)
          .clickable { },
        content = {
          Text(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(R.string.settings_haptics_text),
            style = MaterialTheme.typography.bodyLarge
          )
        },
        trail = { Icon(painter = painterResource(R.drawable.arrow_right), null) }
      )
    }
    item {
      YMSListItem(
        modifier = Modifier
          .fillMaxWidth()
          .height(56.dp)
          .clickable { },
        content = {
          Text(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(R.string.settings_passcode_text),
            style = MaterialTheme.typography.bodyLarge
          )
        },
        trail = { Icon(painter = painterResource(R.drawable.arrow_right), null) }
      )
    }
    item {
      YMSListItem(
        modifier = Modifier
          .fillMaxWidth()
          .height(56.dp)
          .clickable { },
        content = {
          Text(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(R.string.settings_synchronization_text),
            style = MaterialTheme.typography.bodyLarge
          )
        },
        trail = { Icon(painter = painterResource(R.drawable.arrow_right), null) }
      )
    }
    item {
      YMSListItem(
        modifier = Modifier
          .fillMaxWidth()
          .height(56.dp)
          .clickable { },
        content = {
          Text(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(R.string.settings_language_text),
            style = MaterialTheme.typography.bodyLarge
          )
        },
        trail = { Icon(painter = painterResource(R.drawable.arrow_right), null) }
      )
    }
    item {
      YMSListItem(
        modifier = Modifier
          .fillMaxWidth()
          .height(56.dp)
          .clickable { },
        content = {
          Text(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(R.string.settings_about_text),
            style = MaterialTheme.typography.bodyLarge
          )
        },
        trail = { Icon(painter = painterResource(R.drawable.arrow_right), null) }
      )
    }
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
              text = stringResource(R.string.settings_last_sync_text),
              style = MaterialTheme.typography.bodyLarge
            )

            Text(
              text = state.result.ifEmpty { stringResource(R.string.settings_sync_not_yet_performed) },
              style = MaterialTheme.typography.bodyLarge
            )
          }
        }
      )
    }
  }
}