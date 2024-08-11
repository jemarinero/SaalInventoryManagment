package com.saal.domain.repository

import com.saal.domain.common.error.Failure
import com.saal.domain.model.Item
import com.saal.domain.model.ItemRelations
import com.saal.domain.common.result.Result
import kotlinx.coroutines.flow.Flow

interface ItemRepository {

    suspend fun insertOrUpdateItem(item: Item): Result<Item, Failure>
    suspend fun deleteItem(itemId: Long): Result<Unit, Failure>
    suspend fun getItemById(itemId: Long): Result<Item, Failure>
    fun getAllItems(): Flow<List<Item>>
    fun getItemWithRelations(itemId: Long): Flow<ItemRelations>

}