package com.saal.domain.usecase

import com.saal.domain.common.base.BaseUseCase
import com.saal.domain.common.distpatcher.DispatcherProvider
import com.saal.domain.common.error.Failure
import com.saal.domain.common.result.Result
import com.saal.domain.common.result.resultSuccess
import com.saal.domain.model.ItemRelations
import com.saal.domain.repository.ItemRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetSelectedItemUseCase @Inject
constructor(
    private val repository: ItemRepository,
    dispatcher: DispatcherProvider
): BaseUseCase<Long, Result<ItemRelations, Failure>>(dispatcher) {

    override fun configure(param: Long): Flow<Result<ItemRelations, Failure>> = flow {
        repository.getItemWithRelations(param).collect{
            emit(resultSuccess(it))
        }
    }
}