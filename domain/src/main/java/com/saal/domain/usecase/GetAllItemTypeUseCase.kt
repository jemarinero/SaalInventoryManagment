package com.saal.domain.usecase

import com.saal.domain.common.base.BaseUseCase
import com.saal.domain.common.distpatcher.DispatcherProvider
import com.saal.domain.model.ItemType
import com.saal.domain.repository.ItemTypeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetAllItemTypeUseCase
@Inject
constructor(
    private val repository: ItemTypeRepository,
    dispatcher: DispatcherProvider
): BaseUseCase<Unit, List<ItemType>>(dispatcher) {

    override fun configure(param: Unit): Flow<List<ItemType>> = flow {
        repository.getAllItemTypes().collect {
            emit(it)
        }
    }
}