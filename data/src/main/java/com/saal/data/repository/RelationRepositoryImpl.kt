package com.saal.data.repository

import com.saal.data.common.mapper.toEntity
import com.saal.data.local.datasource.LocalStorageDataSource
import com.saal.domain.model.ItemRelation
import com.saal.domain.repository.RelationRepository
import javax.inject.Inject

class RelationRepositoryImpl
@Inject
constructor(
    private val localStorageDataSource: LocalStorageDataSource
): RelationRepository {

    override suspend fun insertRelation(relation: ItemRelation) =
        localStorageDataSource.insertItemRelation(relation.toEntity())

    override suspend fun deleteRelation(relation: ItemRelation) =
        localStorageDataSource.deleteItemRelation(relation.parentItemId, relation.childItemId)
}