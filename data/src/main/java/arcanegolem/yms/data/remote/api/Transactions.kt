package arcanegolem.yms.data.remote.api

import io.ktor.resources.Resource
import arcanegolem.yms.data.remote.models.Transaction
import arcanegolem.yms.data.remote.models.TransactionResponse


/**
 * Эндпоинт транзакций
 *
 * - при обращении через POST создает [Transaction]
 */
@Resource("/transactions")
internal class Transactions {
  /**
   * Эндпоинт для взаимодейстия с транзакцией по идентификатору
   *
   * - при обращении через GET возвращает список [TransactionResponse] с соответствующим идентификатором
   * - при обращении через PUT обновляет [Transaction]
   * - при обращении через DELETE удаляет [Transaction]
   *
   * @param parent родительский элемент ссылки
   * @param id идентификатор транзакции
   */
  @Resource("{id}")
  class Id(
    val parent : Transactions = Transactions(),
    val id : Int
  )
  @Resource("account")
  class Account(val parent: Transactions = Transactions()) {
    /**
     * Задает идентификатор счета
     *
     * @param parent родительский элемент ссылки
     * @param accountId идентификатор счета
     */
    @Resource("{accountId}")
    class ById(
      val parent: Account = Account(),
      val accountId : Int
    ) {
      /**
       * Эндпоинт для получения транзакций для определенного идентификатором счета за определенный период времени
       *
       * - при обращении через GET возвращает список [TransactionResponse]
       *
       * @param parent родительский элемент ссылки см. [Transactions.Account.ById]
       * @param startDate дата в формате YYYY-MM-DD начала периода
       * @param endDate дата в формате YYYY-MM-DD конца периода
       */
      @Resource("period")
      class Period(
        val parent : ById,
        val startDate : String? = null,
        val endDate : String? = null
      )
    }
  }
}