package com.saal.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.saal.data.local.entity.ItemRelationEntity

@Dao
interface ItemRelationDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vararg objects: ItemRelationEntity)

    @Query("DELETE from ItemRelationEntity where parentItemId = :parentItemId AND childItemId = :childItemId")
    suspend fun delete(parentItemId: Long, childItemId: Long)

}
