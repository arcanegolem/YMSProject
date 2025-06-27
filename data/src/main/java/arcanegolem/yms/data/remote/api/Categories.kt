package arcanegolem.yms.data.remote.api

import arcanegolem.yms.data.remote.models.Category
import io.ktor.resources.Resource

/**
 * Эндпоинт для получения всех статей, при обращении через GET возвращает список [Category]
 */
@Resource("/categories")
internal class Categories {
  @Resource("type")
  class ByType(val parent : Categories = Categories()) {
    /**
     * Эндпоинт для получения списка статей отфильтрованных по признаку прибыль/расход, при обращении через GET возвращает список [Category]
     *
     * @param parent родительский элемент ссылки
     * @param isIncome флаг прибыль(true)/расход(false)
     */
    @Resource("{isIncome}")
    class IsIncome(
      val parent : ByType = ByType(),
      val isIncome : Boolean
    )
  }
}
