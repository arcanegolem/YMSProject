package arcanegolem.yms.core.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import arcanegolem.yms.core.data.remote.helpers.PayloadObject
import kotlinx.serialization.json.Json

@Entity("sync_queue")
data class QueueEntity (
  @PrimaryKey val identifier : String,
  val type : String, // Строковое представление [QueueOperationType]
  val payload : String, // Строковое представление пейлоада которое будет отправлено
  val arbitraryId : Int = -1
) {
  inline fun <reified T : PayloadObject> payloadAsObject() : T {
    return Json.decodeFromString<T>(payload)
  }
}

enum class QueueOperationType(private val stringRepresentation : String) {
  TRANSACTION_UPDATE("TRANSACTION_UPDATE"),
  TRANSACTION_CREATE("TRANSACTION_CREATE"),
  TRANSACTION_DELETE("TRANSACTION_DELETE"),
  ACCOUNT_UPDATE("ACCOUNT_UPDATE");

  operator fun invoke() : String {
    return stringRepresentation
  }

  companion object {
    fun fromString(string: String): QueueOperationType {
      return when (string) {
        "TRANSACTION_UPDATE" -> TRANSACTION_UPDATE
        "TRANSACTION_CREATE" -> TRANSACTION_CREATE
        "TRANSACTION_DELETE" -> TRANSACTION_DELETE
        "ACCOUNT_UPDATE" -> ACCOUNT_UPDATE
        else -> throw IllegalArgumentException("Non-existent QueueOperationType [$string]!")
      }
    }
  }
}