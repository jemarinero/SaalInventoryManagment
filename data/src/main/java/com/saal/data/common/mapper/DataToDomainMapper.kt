package com.saal.data.common.mapper

import com.saal.data.local.entity.ItemTypeEntity
import com.saal.data.local.entity.ItemWithRelations
import com.saal.data.local.entity.ItemWithType
import com.saal.domain.model.Item
import com.saal.domain.model.ItemRelations
import com.saal.domain.model.ItemType

fun ItemWithType.toDomain() = Item(
        itemId = item.itemId,
        name = item.name,
        description = item.description,
        itemType = itemType.toDomain()
)

fun ItemTypeEntity.toDomain() = ItemType(
    typeId = typeId,
    name = name
)

fun ItemWithRelations.toDomain() = ItemRelations(
    item = item.toDomain(),
    relations = relations.map { it.toDomain() }
)