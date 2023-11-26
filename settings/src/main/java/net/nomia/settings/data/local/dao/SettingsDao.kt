package net.nomia.settings.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow
import net.nomia.settings.data.local.entity.SettingsEntity

@Dao
interface SettingsDao {

    @Transaction
    @Query("SELECT * FROM settings LIMIT 1")
    fun findFirst(): Flow<SettingsEntity?>

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(vararg entities: SettingsEntity)

    @Transaction
    @Delete
    suspend fun delete(vararg entities: SettingsEntity)

    @Transaction
    @Query("DELETE FROM settings")
    suspend fun deleteAll()
}
