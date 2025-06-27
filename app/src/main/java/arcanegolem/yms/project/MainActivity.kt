package arcanegolem.yms.project

import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import arcanegolem.yms.project.ui.YMSProjectRoot
import arcanegolem.yms.project.ui.theme.YMSProjectTheme
import arcanegolem.yms.project.util.network.NetworkMonitor

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
      YMSProjectTheme {
        YMSProjectRoot()
      }
    }
  }
}
