package com.saal.domain.usecase

import com.saal.domain.common.base.BaseUseCase
import com.saal.domain.common.distpatcher.DispatcherProvider
import com.saal.domain.model.ItemRelation
import com.saal.domain.model.OperationType
import com.saal.domain.repository.RelationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class ManageRelationUseCase@Inject
constructor(
    private val repository: RelationRepository,
    dispatcher: DispatcherProvider
) : BaseUseCase<ManageRelationUseCase.ManageRelationParam, Unit>(dispatcher) {

    override fun configure(param: ManageRelationParam): Flow<Unit> = flow {
        emit(
            when (param.operationType) {
                OperationType.CREATE,
                OperationType.UPDATE -> repository.insertRelation(param.relation)
                OperationType.DELETE -> repository.deleteRelation(param.relation)
            }
        )
    }

    data class ManageRelationParam(
        val relation: ItemRelation,
        val operationType: OperationType
    )
}