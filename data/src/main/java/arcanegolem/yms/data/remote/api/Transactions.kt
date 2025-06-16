package arcanegolem.yms.data.remote.api

import io.ktor.resources.Resource

@Resource("/transactions")
class Transactions {
  @Resource("{id}")
  class Id(
    val parent : Transactions = Transactions(),
    val id : Int
  )

  @Resource("account")
  class Account(val parent: Transactions = Transactions()) {
    @Resource("{accountId}")
    class ById(
      val parent: Account = Account(),
      val accountId : Int
    ) {
      @Resource("period")
      class Period(
        val parent : ById,
        val startDate : String? = null,
        val endDate : String? = null
      )
    }
  }
}