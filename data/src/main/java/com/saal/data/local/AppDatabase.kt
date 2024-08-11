package com.saal.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.saal.data.local.dao.ItemDao
import com.saal.data.local.dao.ItemRelationDao
import com.saal.data.local.dao.ItemTypeDao
import com.saal.data.local.entity.ItemEntity
import com.saal.data.local.entity.ItemRelationEntity
import com.saal.data.local.entity.ItemTypeEntity

@Database(entities = [ItemEntity::class, ItemTypeEntity::class, ItemRelationEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun itemDao(): ItemDao
    abstract fun itemTypeDao(): ItemTypeDao
    abstract fun itemRelationDao(): ItemRelationDao
}