package com.saal.data.common.mapper

import com.saal.data.local.entity.ItemEntity
import com.saal.data.local.entity.ItemRelationEntity
import com.saal.data.local.entity.ItemTypeEntity
import com.saal.domain.model.Item
import com.saal.domain.model.ItemRelation
import com.saal.domain.model.ItemType

fun Item.toEntity() = ItemEntity(
    itemId = itemId,
    name = name,
    description = description,
    itemTypeId = itemType.typeId,
)

fun ItemType.toEntity() = ItemTypeEntity(
    typeId = typeId,
    name = name
)

fun ItemRelation.toEntity() = ItemRelationEntity(
    parentItemId = parentItemId,
    childItemId = childItemId
)