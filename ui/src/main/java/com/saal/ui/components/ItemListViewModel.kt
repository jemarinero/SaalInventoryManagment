package com.saal.ui.components

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.saal.domain.model.Item
import com.saal.domain.usecase.GetAllItemUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class ItemListViewModel
@Inject
constructor(
    private val getAllItemUseCase: GetAllItemUseCase,
) : ViewModel() {

    var items = MutableStateFlow<List<Item>>(emptyList())
    private  set

    init {
        getAllItems()
    }

    private fun getAllItems() =
        getAllItemUseCase
            .execute(Unit)
            .catch {  }
            .onEach {
                items.value = it
            }
            .launchIn(viewModelScope)
}