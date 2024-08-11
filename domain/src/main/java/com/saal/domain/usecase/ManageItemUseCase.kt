package com.saal.domain.usecase

import com.saal.domain.common.base.BaseUseCase
import com.saal.domain.common.distpatcher.DispatcherProvider
import com.saal.domain.common.error.Failure
import com.saal.domain.common.result.Result
import com.saal.domain.common.result.resultSuccess
import com.saal.domain.model.Item
import com.saal.domain.model.OperationType
import com.saal.domain.repository.ItemRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ManageItemUseCase @Inject
constructor(
    private val repository: ItemRepository,
    dispatcher: DispatcherProvider
) : BaseUseCase<ManageItemUseCase.ManageItemParam, Result<Item, Failure>>(dispatcher) {

    override fun configure(param: ManageItemParam): Flow<Result<Item, Failure>> = flow {
        emit(
            when (param.operationType) {
                OperationType.CREATE,
                OperationType.UPDATE -> repository.insertOrUpdateItem(param.item)
                OperationType.DELETE -> {
                    repository.deleteItem(param.item.itemId)
                    resultSuccess(param.item)
                }
            }
        )
    }

    data class ManageItemParam(
        val item: Item,
        val operationType: OperationType
    )
}

