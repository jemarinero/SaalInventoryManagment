package com.saal.data.local.entity

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class ItemWithRelations(
    @Embedded
    val item: ItemWithType,
    @Relation(
        parentColumn = "itemId",
        entity = ItemEntity::class,
        entityColumn = "itemId",
        associateBy = Junction(
            value = ItemRelationEntity::class,
            parentColumn = "parentItemId",
            entityColumn = "childItemId"
        )
    )
    val relations: List<ItemWithType>

)
