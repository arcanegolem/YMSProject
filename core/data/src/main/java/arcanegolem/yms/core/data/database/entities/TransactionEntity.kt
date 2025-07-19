package arcanegolem.yms.core.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("transactions")
data class TransactionEntity(
  @PrimaryKey(autoGenerate = false)
  val transactionId : Int,
  val categoryId : Int,
  val amount : String,
  val transactionDateMillis : Long,
  val createdAt : String,
  val updatedAt : String = "",
  val comment : String = "",
  val isIncome : Boolean
)
