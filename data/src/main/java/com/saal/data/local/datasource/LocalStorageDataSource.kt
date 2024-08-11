package com.saal.data.local.datasource

import android.util.Log
import com.saal.data.common.exception.DatabaseException
import com.saal.data.local.AppDatabase
import com.saal.data.local.entity.ItemEntity
import com.saal.data.local.entity.ItemRelationEntity
import com.saal.data.local.entity.ItemTypeEntity
import com.saal.data.local.entity.ItemWithRelations
import com.saal.data.local.entity.ItemWithType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import kotlin.jvm.Throws

class LocalStorageDataSource
@Inject
constructor(private val appDatabase: AppDatabase) {

    //region Item
    @Throws(DatabaseException::class)
    suspend fun insertOrUpdateItem(item: ItemEntity): Long {
        return try {
            appDatabase.itemDao().insertOrUpdate(item)
        } catch (ex: Exception) {
            throw DatabaseException(ex.message)
        }

    }

    @Throws(DatabaseException::class, Exception::class)
    fun getAllItems(): Flow<List<ItemWithType>> = flow {
        try {
            appDatabase.itemDao().getAllItems().collect{
                emit(it)
            }
        } catch (ex: Exception) {
            throw DatabaseException(ex.message)
        }
    }

    @Throws(DatabaseException::class, Exception::class)
    suspend fun getItem(itemId: Long): ItemWithType {
        return try {
            appDatabase.itemDao().getItemById(itemId)
        } catch (ex: Exception) {
            throw DatabaseException(ex.message)
        }
    }

    @Throws(DatabaseException::class, Exception::class)
    suspend fun deleteById(itemId: Long) {
        try {
            appDatabase.itemDao().deleteById(itemId)
        } catch (ex: Exception) {
            throw DatabaseException(ex.message)
        }
    }

    @Throws(DatabaseException::class, Exception::class)
    fun getItemWithRelations(itemId: Long): Flow<ItemWithRelations> = flow {
        try {
            appDatabase.itemDao().getItemWithRelations(itemId).collect {
                emit(it)
            }
        } catch (ex: Exception) {
            throw DatabaseException(ex.message)
        }
    }
    //endregion

    //region ItemType
    @Throws(DatabaseException::class, Exception::class)
    suspend fun insertOrUpdateItemType(itemType: ItemTypeEntity){
        try {
            appDatabase.itemTypeDao().insertOrUpdate(itemType)
        } catch (ex: Exception) {
            throw DatabaseException(ex.message)
        }
    }

    @Throws(DatabaseException::class, Exception::class)
    fun getAllItemTypes(): Flow<List<ItemTypeEntity>> = flow {
        try {
            appDatabase.itemTypeDao().getAllItemTypes().collect {
                emit(it)
            }
        } catch (ex: Exception) {
            throw DatabaseException(ex.message)
        }
    }

    @Throws(DatabaseException::class, Exception::class)
    suspend fun deleteItemType(itemType: ItemTypeEntity){
        try {
            appDatabase.itemTypeDao().delete(itemType)
        } catch (ex: Exception) {
            throw DatabaseException(ex.message)
        }
    }
    //endregion

    //region ItemRelation
    @Throws(DatabaseException::class, Exception::class)
    suspend fun insertItemRelation(itemRelation: ItemRelationEntity){
        try {
            appDatabase.itemRelationDao().insert(itemRelation)
        } catch (ex: Exception) {
            throw DatabaseException(ex.message)
        }
    }

    @Throws(DatabaseException::class, Exception::class)
    suspend fun deleteItemRelation(parentItemId: Long, childItemId: Long) {
        try {
            appDatabase.itemRelationDao().delete(parentItemId, childItemId)
        } catch (ex: Exception) {
            throw DatabaseException(ex.message)
        }
    }
    //endregion

}