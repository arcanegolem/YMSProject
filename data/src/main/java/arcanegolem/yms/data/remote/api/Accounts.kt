package arcanegolem.yms.data.remote.api

import io.ktor.resources.Resource
import arcanegolem.yms.data.remote.models.Account

/**
 * Эндпоинт счетов
 *
 * - при обращении через GET возвращает список [Account]
 * - при обращении через POST создает [Account]
 */
@Resource("/accounts")
internal class Accounts {
  /**
   * Эндпоинт для взаимодействия с конкретным счетом по идентификатору
   *
   * - при обращении через GET возвращает [Account]
   * - при обращении через PUT обновляет данные [Account]
   * - при обращении через DELETE удаляет [Account]
   *
   * @param parent родительский элемент ссылки
   * @param id идентификатор счета
   */
  @Resource("{id}")
  class Id(
    val parent : Accounts = Accounts(),
    val id : Int
  ) {
    /**
     * Эндпоинт для получения истории определенного идентификатором счета, при обращении через
     * GET возвращает (Not implemented)
     *
     * TODO: Добавить модель для аккаунта с историей
     *
     * @param parent родительский элемент ссылки с праметром в виде идентификатора см. [Accounts.Id]
     */
    @Resource("history")
    class History(val parent: Id)
  }
}
