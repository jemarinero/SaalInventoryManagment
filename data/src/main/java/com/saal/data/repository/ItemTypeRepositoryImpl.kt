package com.saal.data.repository

import com.saal.data.common.exception.DatabaseException
import com.saal.data.common.mapper.toDomain
import com.saal.data.common.mapper.toEntity
import com.saal.data.local.datasource.LocalStorageDataSource
import com.saal.domain.common.error.Failure
import com.saal.domain.common.result.Result
import com.saal.domain.common.result.resultError
import com.saal.domain.common.result.resultSuccess
import com.saal.domain.model.ItemType
import com.saal.domain.repository.ItemTypeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ItemTypeRepositoryImpl
@Inject
constructor(
    private val localStorageDataSource: LocalStorageDataSource
) : ItemTypeRepository {

    override suspend fun insertOrUpdateItemType(itemType: ItemType): Result<Unit, Failure> {
        return try {
            resultSuccess(localStorageDataSource.insertOrUpdateItemType(itemType.toEntity()))
        } catch (ex: DatabaseException) {
            resultError(Failure.DataError(ex.message))
        } catch (ex: Exception) {
            resultError(Failure.GenericError(ex.message))
        }
    }

    override fun getAllItemTypes(): Flow<List<ItemType>> = flow {
        localStorageDataSource.getAllItemTypes().collect {
            emit(it.map { itemTypeEntity -> itemTypeEntity.toDomain() })
        }
    }

    override suspend fun deleteItemType(itemType: ItemType): Result<Unit, Failure> {
        return try {
            resultSuccess(localStorageDataSource.deleteItemType(itemType.toEntity()))
        } catch (ex: DatabaseException) {
            resultError(Failure.DataError(ex.message))
        } catch (ex: Exception) {
            resultError(Failure.GenericError(ex.message))
        }
    }
}