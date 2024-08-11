package com.saal.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.saal.data.local.entity.ItemEntity
import com.saal.data.local.entity.ItemWithRelations
import com.saal.data.local.entity.ItemWithType
import kotlinx.coroutines.flow.Flow

@Dao
interface ItemDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdate(item: ItemEntity): Long

    @Query("DELETE from ItemEntity where itemId = :itemId")
    suspend fun deleteById(itemId: Long)

    @Query("SELECT * FROM ItemEntity")
    fun getAllItems() : Flow<List<ItemWithType>>

    @Query("SELECT * FROM ItemEntity where itemId = :itemId")
    suspend fun getItemById(itemId: Long): ItemWithType

    @Transaction
    @Query("SELECT * FROM ItemEntity where itemId = :itemId")
    fun getItemWithRelations(itemId: Long) : Flow<ItemWithRelations>
}