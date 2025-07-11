package arcanegolem.yms.core.ui.components.bottom_bar

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

/**
 * Модель элемента для нижнего навигационного меню
 *
 * @param titleId заголовок в виде id ресурса [StringRes]
 * @param iconId иконка в виде id ресурса [DrawableRes]
 * @param destination направление навигации в виде класса
 */
data class YMSDestinationModel<T : Any>(
  @param:StringRes val titleId: Int,
  @param:DrawableRes val iconId : Int,
  val destination : T
)
