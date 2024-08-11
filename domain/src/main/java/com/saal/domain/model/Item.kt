package com.saal.domain.model

data class Item(
    val itemId: Long = 0L,
    val name: String,
    val description: String,
    val itemType: ItemType
)
