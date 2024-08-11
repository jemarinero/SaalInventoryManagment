package com.saal.data.local.entity

import androidx.room.Embedded
import androidx.room.Relation

data class ItemWithType(
    @Embedded
    val item: ItemEntity,
    @Relation(
        parentColumn = "itemTypeId",
        entityColumn = "typeId",
    )
    val itemType: ItemTypeEntity
)
