package arcanegolem.yms.project.common

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

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
    onClick = onClick
  ) {
    content()
  }
}