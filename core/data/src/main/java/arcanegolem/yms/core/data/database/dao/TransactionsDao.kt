package arcanegolem.yms.core.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import arcanegolem.yms.core.data.database.entities.ArbitraryTransactionEntity
import arcanegolem.yms.core.data.database.entities.TransactionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TransactionsDao {
  @Query("SELECT * FROM transactions WHERE " +
      "transactionDateMillis >= :periodStart AND " +
      "transactionDateMillis <= :periodEnd AND " +
      "isIncome = :isIncome")
  fun getTransactionsForPeriod(
    isIncome : Boolean,
    periodStart : Long,
    periodEnd : Long
  ) : Flow<List<TransactionEntity>>
  
  @Query("SELECT * FROM transactions WHERE " +
      "transactionDateMillis >= :periodStart AND " +
      "transactionDateMillis <= :periodEnd")
  fun getAllTransactionsForPeriod(
    periodStart : Long,
    periodEnd : Long
  ) : Flow<List<TransactionEntity>>

  @Query("SELECT * FROM transactions")
  suspend fun getAllTransactions() : List<TransactionEntity>

  @Upsert suspend fun upsertTransaction(transaction: TransactionEntity)
  @Delete suspend fun deleteTransaction(transaction: TransactionEntity)

  @Query("DELETE FROM transactions WHERE transactionId = :id")
  suspend fun deleteByTransactionId(id : Int)

  @Query("SELECT * FROM transactions WHERE transactionId = :id")
  suspend fun getByTransactionId(id : Int) : TransactionEntity?

  @Query("SELECT * FROM arbitrary_transactions WHERE " +
      "transactionDateMillis >= :periodStart AND " +
      "transactionDateMillis <= :periodEnd AND " +
      "isIncome = :isIncome")
  fun getArbitraryTransactionsForPeriod(
    isIncome : Boolean,
    periodStart : Long,
    periodEnd : Long
  ) : Flow<List<ArbitraryTransactionEntity>>
  
  @Query("SELECT * FROM arbitrary_transactions WHERE " +
    "transactionDateMillis >= :periodStart AND " +
        "transactionDateMillis <= :periodEnd")
  fun getAllArbitraryTransactionsForPeriod(
    periodStart : Long,
    periodEnd : Long
  ) : Flow<List<ArbitraryTransactionEntity>>

  @Upsert suspend fun upsertArbitraryTransaction(transactionEntity: ArbitraryTransactionEntity) : Long
  @Delete suspend fun deleteArbitraryTransaction(transaction: ArbitraryTransactionEntity)

  @Query("SELECT * FROM arbitrary_transactions WHERE arbitraryId = :id")
  suspend fun getByArbitraryTransactionId(id : Int) : ArbitraryTransactionEntity?
}