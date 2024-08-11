package com.saal.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ItemTypeEntity(
    @PrimaryKey(autoGenerate = true)
    val typeId: Long = 0L,
    @ColumnInfo(name = "name")
    val name: String
)
