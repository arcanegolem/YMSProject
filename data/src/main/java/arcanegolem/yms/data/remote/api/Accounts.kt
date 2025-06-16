package arcanegolem.yms.data.remote.api

import io.ktor.resources.Resource

@Resource("/accounts")
class Accounts {
  @Resource("{id}")
  class Id(
    val parent : Accounts = Accounts(),
    val id : Int
  ) {
    @Resource("history")
    class History(val parent: Id)
  }
}