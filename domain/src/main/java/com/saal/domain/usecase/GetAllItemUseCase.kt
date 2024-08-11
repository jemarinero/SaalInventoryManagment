package com.saal.domain.usecase

import com.saal.domain.common.base.BaseUseCase
import com.saal.domain.common.distpatcher.DispatcherProvider
import com.saal.domain.model.Item
import com.saal.domain.repository.ItemRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetAllItemUseCase
@Inject
constructor(
    private val repository: ItemRepository,
    dispatcher: DispatcherProvider
): BaseUseCase<Unit, List<Item>>(dispatcher) {

    override fun configure(param: Unit): Flow<List<Item>> = flow {
        repository.getAllItems().collect {
            emit(it)
        }
    }
}