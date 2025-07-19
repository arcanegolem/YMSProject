package arcanegolem.yms.core.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import arcanegolem.yms.core.data.database.dao.CategoryDao
import arcanegolem.yms.core.data.database.dao.QueueDao
import arcanegolem.yms.core.data.database.dao.TransactionsDao
import arcanegolem.yms.core.data.database.entities.ArbitraryTransactionEntity
import arcanegolem.yms.core.data.database.entities.CategoryEntity
import arcanegolem.yms.core.data.database.entities.QueueEntity
import arcanegolem.yms.core.data.database.entities.TransactionEntity

@Database(
  entities = [
    TransactionEntity::class,
    CategoryEntity::class,
    QueueEntity::class,
    ArbitraryTransactionEntity::class
  ],
  version = 1
)
abstract class YMSDatabase : RoomDatabase() {

  abstract fun transactionDao() : TransactionsDao
  abstract fun categoryDao() : CategoryDao
  abstract fun queueDao() : QueueDao

  companion object {
    private const val DB_NAME = "yms_proj.db"

    @Volatile
    private var instance : YMSDatabase? = null

    fun getInstance(context : Context) : YMSDatabase {
      if (instance == null) {
        instance = Room.databaseBuilder(context, YMSDatabase::class.java, DB_NAME)
          .build()
      }
      return instance as YMSDatabase
    }
  }
}