package arcanegolem.yms.settings.ui.color_chooser.state_handlers

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import arcanegolem.yms.core.ui.components.YMSListItem
import arcanegolem.yms.settings.ui.color_chooser.ColorChooserEvent
import arcanegolem.yms.settings.ui.color_chooser.ColorChooserState
import arcanegolem.yms.settings.ui.color_chooser.models.colorModels

@Composable
fun TargetColorChooserState(
  state : ColorChooserState.Target,
  eventProcessor : (ColorChooserEvent) -> Unit
) {
  val colorShowcaseSize = 24.dp
  val colorShowcaseBoxWidth = colorShowcaseSize * 1.5f
  
  LazyColumn(
    modifier = Modifier.fillMaxSize()
  ) {
    items(items = colorModels) { model ->
      YMSListItem(
        modifier = Modifier
          .fillMaxWidth()
          .height(72.dp)
          .clickable {
            eventProcessor(ColorChooserEvent.SetColors(model.primaryHex, model.secondaryHex))
          },
        lead = {
          Box(
            modifier = Modifier
              .height(colorShowcaseSize)
              .width(colorShowcaseBoxWidth)
          ) {
            Box(
              modifier = Modifier
                .size(colorShowcaseSize)
                .align(Alignment.CenterEnd)
                .clip(CircleShape)
                .background(Color(model.secondaryHex))
            )
            Box(
              modifier = Modifier
                .size(colorShowcaseSize)
                .align(Alignment.CenterStart)
                .clip(CircleShape)
                .background(Color(model.primaryHex))
            )
          }
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
            selected = model.primaryHex == state.selectedPrimaryColorHex,
            onClick = {
              eventProcessor(ColorChooserEvent.SetColors(model.primaryHex, model.secondaryHex))
            }
          )
        }
      )
    }
  }
}