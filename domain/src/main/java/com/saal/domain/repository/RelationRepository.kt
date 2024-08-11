package com.saal.domain.repository

import com.saal.domain.model.ItemRelation

interface RelationRepository {

    suspend fun insertRelation(relation: ItemRelation)
    suspend fun deleteRelation(relation: ItemRelation)
}