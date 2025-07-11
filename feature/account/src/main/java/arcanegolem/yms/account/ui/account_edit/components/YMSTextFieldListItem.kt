package arcanegolem.yms.account.ui.account_edit.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import arcanegolem.yms.core.ui.components.YMSListItem

@Composable
fun YMSTextFieldListItem(
  focusManager : FocusManager,
  descText : String,
  text : String,
  keyboardType: KeyboardType,
  onConsumeChanges : (String) -> Unit,
  visualTransformation: VisualTransformation = VisualTransformation.None
) {
  val focusRequester = remember { FocusRequester() }

  YMSListItem(
    modifier = Modifier
      .fillMaxWidth()
      .height(56.dp)
      .clickable {
        focusRequester.requestFocus()
      },
    lead = {
      Text(
        text = descText,
        style = MaterialTheme.typography.bodyLarge,
        color = MaterialTheme.colorScheme.onSurface
      )
    },
    content = {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.End
      ) {
        BasicTextField(
          modifier = Modifier.focusRequester(focusRequester),
          value = text,
          onValueChange = { onConsumeChanges(it) },
          textStyle = MaterialTheme.typography.bodyLarge.copy(textAlign = TextAlign.End),
          keyboardOptions = KeyboardOptions(
            keyboardType = keyboardType,
            imeAction = ImeAction.Done
          ),
          visualTransformation = visualTransformation,
          keyboardActions = KeyboardActions(
            onDone = { focusManager.clearFocus() }
          ),
          singleLine = true
        )
      }
    }
  )
}