package com.saal.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(
    primaryKeys = ["parentItemId", "childItemId"],
    foreignKeys = [
        ForeignKey(
            entity = ItemEntity::class,
            parentColumns = ["itemId"],
            childColumns = ["parentItemId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = ItemEntity::class,
            parentColumns = ["itemId"],
            childColumns = ["childItemId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class ItemRelationEntity(
    val parentItemId: Long,
    val childItemId: Long
)
