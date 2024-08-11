package com.saal.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.saal.data.local.entity.ItemTypeEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ItemTypeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdate(itemType: ItemTypeEntity)

    @Delete
    suspend fun delete(itemType: ItemTypeEntity)

    @Query("DELETE FROM ItemTypeEntity WHERE typeId = :typeId")
    suspend fun deleteById(typeId: Long)

    @Query("SELECT * FROM ItemTypeEntity")
    fun getAllItemTypes(): Flow<List<ItemTypeEntity>>
}
