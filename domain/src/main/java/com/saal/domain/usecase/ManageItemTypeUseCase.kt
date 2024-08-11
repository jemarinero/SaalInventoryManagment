package com.saal.domain.usecase

import com.saal.domain.common.base.BaseUseCase
import com.saal.domain.common.distpatcher.DispatcherProvider
import com.saal.domain.common.error.Failure
import com.saal.domain.common.result.Result
import com.saal.domain.model.ItemType
import com.saal.domain.model.OperationType
import com.saal.domain.repository.ItemTypeRepository
import com.saal.domain.usecase.ManageItemTypeUseCase.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ManageItemTypeUseCase
@Inject
constructor(
    private val repository: ItemTypeRepository,
    dispatcher: DispatcherProvider
) : BaseUseCase<ManageItemTypeParam, Result<Unit, Failure>>(dispatcher) {

    override fun configure(param: ManageItemTypeParam): Flow<Result<Unit, Failure>> = flow {
        emit(
            when (param.operationType) {
                OperationType.CREATE,
                OperationType.UPDATE -> repository.insertOrUpdateItemType(param.itemType)
                OperationType.DELETE -> repository.deleteItemType(param.itemType)
            }
        )
    }

    data class ManageItemTypeParam(
        val itemType: ItemType,
        val operationType: OperationType
    )
}

