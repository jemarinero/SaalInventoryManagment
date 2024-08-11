package com.saal.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.saal.domain.model.Item

@Composable
fun ItemListComponent(
    items: List<Item>,
    onItemClick: (Long) -> Unit
) {
    Column {

        items.forEach { item ->
            ItemComponent(item = item) {
                onItemClick(item.itemId)
            }
            Spacer(modifier = Modifier.size(8.dp))
        }

    }
}