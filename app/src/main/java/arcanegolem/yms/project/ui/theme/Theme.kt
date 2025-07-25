package arcanegolem.yms.project.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
  primary = GreenMain,
  secondary = GreenSecondary,
  tertiary = RedVibrant
)

private val LightColorScheme = lightColorScheme(
  primary = GreenMain,
  secondary = GreenSecondary,
  onSecondary = Color.White,
  onPrimary = DarkGray,
  tertiary = RedVibrant,
  background = LightGrayBackground,
  onSurface = DarkGray,
  primaryContainer = GreenMain,
  onPrimaryContainer = Color.White,
  outlineVariant = LightGrayOutlineVariant,
  onSurfaceVariant = LightGrayOnSurfaceVariant,
  errorContainer = RedVibrant,
  onErrorContainer = Color.White

  /* Other default colors to override
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    */
)

@Composable
fun YMSProjectTheme(
  darkTheme: Boolean,
  primaryColorNumber : Long,
  secondaryColorNumber : Long,
  content: @Composable () -> Unit
) {
  val colorScheme = when {
    darkTheme -> DarkColorScheme.copy(
      primary = Color(primaryColorNumber),
      primaryContainer = Color(primaryColorNumber),
      secondary = Color(secondaryColorNumber)
    )
    else -> LightColorScheme.copy(
      primary = Color(primaryColorNumber),
      primaryContainer = Color(primaryColorNumber),
      secondary = Color(secondaryColorNumber)
    )
  }

  MaterialTheme(
    colorScheme = colorScheme,
    typography = Typography,
    content = content
  )
}
