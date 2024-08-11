package com.saal.domain.repository

import com.saal.domain.common.error.Failure
import com.saal.domain.common.result.Result
import com.saal.domain.model.ItemType
import kotlinx.coroutines.flow.Flow

interface ItemTypeRepository {

    suspend fun insertOrUpdateItemType(itemType: ItemType): Result<Unit, Failure>
    fun getAllItemTypes(): Flow<List<ItemType>>
    suspend fun deleteItemType(itemType: ItemType): Result<Unit, Failure>

}