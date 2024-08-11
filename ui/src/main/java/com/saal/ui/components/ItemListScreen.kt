package com.saal.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun ItemListComponent(
    viewModel: ItemListViewModel = hiltViewModel(),
    searchText: CharSequence? = null,
    onItemClick: (Long) -> Unit
) {
    val items = viewModel.items.collectAsStateWithLifecycle()
    val filteredItems = if(searchText.isNullOrEmpty())
        items.value
    else
        items.value.filter { item -> item.name.contains(searchText) || item.description.contains(searchText) }

    Column {
        ItemListComponent(items = filteredItems) {
            onItemClick(it)
        }
    }
}