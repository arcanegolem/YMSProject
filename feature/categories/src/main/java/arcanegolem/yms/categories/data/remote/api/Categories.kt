package arcanegolem.yms.categories.data.remote.api

import io.ktor.resources.Resource
import arcanegolem.yms.core.data.remote.models.Category

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
