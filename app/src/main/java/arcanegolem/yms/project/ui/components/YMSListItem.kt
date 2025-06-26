package arcanegolem.yms.project.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * Компонент элемента списка, для большей кастомизации использовать [YMSListItemLayout]
 *
 * @param modifier [Modifier]
 * @param lead лид, отображается слева компонента
 * @param content основное наполнение, отображается посередине
 * @param trail трейл, отображается справа
 */
@Composable
fun YMSListItem(
  modifier : Modifier = Modifier,
  lead: @Composable () -> Unit = {},
  content: @Composable () -> Unit = {},
  trail: @Composable () -> Unit = {}
) {
  Column {
    Column(
      modifier = modifier
    ) {
      YMSListItemLayout(
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
        lead, content, trail
      )
    }
    HorizontalDivider(thickness = 1.dp)
  }
}