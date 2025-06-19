package arcanegolem.yms.project.common.state_handlers.error

import androidx.annotation.StringRes

data class YMSError(
  @StringRes val description : Int,
  val throwable: Throwable
)