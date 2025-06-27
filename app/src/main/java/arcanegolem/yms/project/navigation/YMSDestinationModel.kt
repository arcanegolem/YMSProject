package arcanegolem.yms.project.navigation

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
  @StringRes val titleId: Int,
  @DrawableRes val iconId : Int,
  val destination : T
)
