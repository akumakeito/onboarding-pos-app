package net.nomia.onboarding.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import net.nomia.onboarding.data.local.entity.StoreData
import java.util.UUID

@Dao
interface StoreDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveStore(store: StoreData)

    @Query("SELECT * FROM stores ORDER BY id DESC LIMIT 1")
    suspend fun getLastAddedStore(): StoreData?

    @Query("UPDATE stores SET uuid = :uuid WHERE id = :id")
    suspend fun updateStoreId(id: Int, uuid: UUID?)

}