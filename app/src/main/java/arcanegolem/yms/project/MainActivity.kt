package arcanegolem.yms.project

import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.getValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import arcanegolem.yms.core.ui.color.GREEN_MAIN_HEX
import arcanegolem.yms.core.ui.color.GREEN_SECONDARY_HEX
import arcanegolem.yms.core.utils.NetworkMonitor
import arcanegolem.yms.project.ui.YMSProjectRoot
import arcanegolem.yms.project.ui.theme.YMSProjectTheme

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    installSplashScreen()
    enableEdgeToEdge(
      statusBarStyle = SystemBarStyle.light(Color.TRANSPARENT, Color.TRANSPARENT),
      navigationBarStyle = SystemBarStyle.light(Color.WHITE, Color.BLACK)
    )
    NetworkMonitor.collectChanges(this)

    setContent {
      val darkTheme by applicationComponent
        .getDarkThemeUseCase()
        .execute()
        .collectAsStateWithLifecycle(false)
      
      val colors by applicationComponent
        .getColorsUseCase()
        .execute(GREEN_MAIN_HEX, GREEN_SECONDARY_HEX)
        .collectAsStateWithLifecycle(Pair(0L, 0L))
      
      YMSProjectTheme(
        darkTheme = darkTheme,
        primaryColorNumber = colors.first,
        secondaryColorNumber = colors.second
      ) {
        YMSProjectRoot()
      }
    }
  }
}
