package com.saal.data.repository

import com.saal.data.common.exception.DatabaseException
import com.saal.data.common.mapper.toDomain
import com.saal.data.common.mapper.toEntity
import com.saal.data.local.datasource.LocalStorageDataSource
import com.saal.domain.common.error.Failure
import com.saal.domain.common.result.Result
import com.saal.domain.common.result.resultError
import com.saal.domain.common.result.resultSuccess
import com.saal.domain.model.Item
import com.saal.domain.model.ItemRelations
import com.saal.domain.repository.ItemRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ItemRepositoryImpl
@Inject
constructor(
    private val localStorageDataSource: LocalStorageDataSource
) : ItemRepository {

    override suspend fun insertOrUpdateItem(item: Item): Result<Item, Failure> {
        return try {
            val itemId = localStorageDataSource.insertOrUpdateItem(item.toEntity())
            resultSuccess(localStorageDataSource.getItem(itemId).toDomain())
        } catch (ex: DatabaseException) {
            resultError(Failure.DataError(ex.message))
        } catch (ex: Exception) {
            resultError(Failure.GenericError(ex.message))
        }
    }

    override suspend fun getItemById(itemId: Long): Result<Item, Failure> {
        return try {
            resultSuccess(localStorageDataSource.getItem(itemId).toDomain())
        } catch (ex: DatabaseException) {
            resultError(Failure.DataError(ex.message))
        } catch (ex: Exception) {
            resultError(Failure.GenericError(ex.message))
        }
    }

    override suspend fun deleteItem(itemId: Long): Result<Unit, Failure>{
        return try {
            resultSuccess(localStorageDataSource.deleteById(itemId))
        } catch (ex: DatabaseException) {
            resultError(Failure.DataError(ex.message))
        } catch (ex: Exception) {
            resultError(Failure.GenericError(ex.message))
        }
    }

    override fun getAllItems(): Flow<List<Item>> = flow {
        localStorageDataSource.getAllItems().collect {
            emit(it.map { itemEntity -> itemEntity.toDomain() })
        }
    }

    override fun getItemWithRelations(itemId: Long): Flow<ItemRelations> = flow {
        localStorageDataSource.getItemWithRelations(itemId).collect {
            emit(it.toDomain())
        }
    }
}