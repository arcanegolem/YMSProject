package arcanegolem.yms.core.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("arbitrary_transactions")
data class ArbitraryTransactionEntity(
  @PrimaryKey(autoGenerate = true)
  val arbitraryId : Int = 0,
  val categoryId : Int,
  val amount : String,
  val transactionDateMillis : Long,
  val createdAt : String,
  val updatedAt : String = createdAt,
  val comment : String = "",
  val isIncome : Boolean
)
