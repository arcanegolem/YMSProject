package arcanegolem.yms.data.remote.api

import io.ktor.resources.Resource

@Resource("/categories")
class Categories {
  @Resource("type")
  class ByType(val parent : Categories = Categories()) {
    @Resource("{isIncome}")
    class IsIncome(
      val parent : ByType = ByType(),
      val isIncome : Boolean
    )
  }
}