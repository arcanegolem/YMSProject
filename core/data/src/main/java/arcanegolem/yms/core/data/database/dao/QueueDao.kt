package arcanegolem.yms.core.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import arcanegolem.yms.core.data.database.entities.QueueEntity

@Dao
interface QueueDao {
  @Query("SELECT * FROM sync_queue")
  suspend fun getQueue() : List<QueueEntity>

  @Upsert
  suspend fun upsertToQueue(entity: QueueEntity)

  @Delete
  suspend fun deleteFromQueue(entity: QueueEntity)
}