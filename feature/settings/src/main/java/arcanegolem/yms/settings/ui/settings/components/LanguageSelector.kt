package arcanegolem.yms.settings.ui.settings.components

import android.app.LocaleManager
import android.os.Build
import android.os.LocaleList
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.content.getSystemService
import androidx.core.os.LocaleListCompat
import arcanegolem.yms.core.ui.components.YMSListItem
import arcanegolem.yms.settings.ui.settings.models.languageModels
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LanguageSelector(
  isShown : Boolean,
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
  
  if (isShown) {
    ModalBottomSheet(
      sheetState = state,
      onDismissRequest = onDismissLocal
    ) {
      Column(
        modifier = Modifier
          .fillMaxWidth()
      ) {
        languageModels.forEach { model ->
          YMSListItem(
            modifier = Modifier
              .fillMaxWidth()
              .height(56.dp)
              .clickable {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                  context.getSystemService<LocaleManager>()?.applicationLocales =
                    LocaleList.forLanguageTags(model.languageCode)
                } else {
                  AppCompatDelegate.setApplicationLocales(
                    LocaleListCompat.forLanguageTags(model.languageCode)
                  )
                }
              },
            content = {
              Text(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(model.nameResId),
                style = MaterialTheme.typography.bodyLarge
              )
            }
          )
        }
      }
    }
  }
}