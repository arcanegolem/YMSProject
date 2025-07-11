package arcanegolem.yms.core.ui.components.state_handlers.error

import androidx.annotation.StringRes

/**
 * Модель ошибки для отображения
 *
 * @param description описание ошибки в виде id ресурса [StringRes]
 * @param throwable исключение которое было выброшено
 */
data class YMSError(
  @param:StringRes val description : Int,
  val throwable: Throwable
)
