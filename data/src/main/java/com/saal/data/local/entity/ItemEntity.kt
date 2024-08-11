package com.saal.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = ItemTypeEntity::class,
            parentColumns = ["typeId"],
            childColumns = ["itemTypeId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class ItemEntity(
    @PrimaryKey(autoGenerate = true)
    val itemId: Long,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "description")
    val description: String,
    @ColumnInfo(name = "itemTypeId")
    val itemTypeId: Long
)