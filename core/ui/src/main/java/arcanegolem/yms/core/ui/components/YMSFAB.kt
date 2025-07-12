package arcanegolem.yms.core.ui.components

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * Стилизованный FAB на основе Material3 [FloatingActionButton]
 *
 * @param modifier [Modifier]
 * @param onClick колбек при нажатии
 * @param content наполнение кнопки
 */
@Composable
fun YMSFAB(
  modifier : Modifier,
  onClick : () -> Unit,
  content : @Composable () -> Unit
) {
  FloatingActionButton(
    modifier = modifier,
    shape = CircleShape,
    elevation = FloatingActionButtonDefaults.elevation(0.dp, 0.dp, 0.dp, 0.dp),
    onClick = onClick,
    contentColor = MaterialTheme.colorScheme.onPrimaryContainer
  ) {
    content()
  }
}
