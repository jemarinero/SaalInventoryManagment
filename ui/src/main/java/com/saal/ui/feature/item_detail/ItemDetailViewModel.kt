package com.saal.ui.feature.item_detail

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.saal.domain.common.result.onFailure
import com.saal.domain.common.result.onSuccess
import com.saal.domain.model.Item
import com.saal.domain.model.ItemRelation
import com.saal.domain.model.ItemType
import com.saal.domain.model.OperationType
import com.saal.domain.usecase.GetAllItemTypeUseCase
import com.saal.domain.usecase.GetSelectedItemUseCase
import com.saal.domain.usecase.ManageItemUseCase
import com.saal.domain.usecase.ManageRelationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class ItemDetailViewModel
@Inject
constructor(
    private val manageItemUseCase: ManageItemUseCase,
    private val getAllItemTypeUseCase: GetAllItemTypeUseCase,
    private val getSelectedItemUseCase: GetSelectedItemUseCase,
    private val manageRelationUseCase: ManageRelationUseCase
): ViewModel() {

    var selectedItemId: Long? = null

    val itemTypes = mutableStateOf<List<ItemType>>(emptyList())
    var itemRelations = mutableStateOf<List<Item>>(emptyList())
    val showSnackBack = mutableStateOf<OperationType?>(null)

    var state = MutableStateFlow<Item?>(null)
    private set

    init {
        getAllItemTypes()
    }

    private fun getAllItemTypes() =
        getAllItemTypeUseCase
            .execute(Unit)
            .catch { }
            .onEach { items ->
                itemTypes.value = items
            }
            .launchIn(viewModelScope)

    fun manageItem(name: String, description: String, itemType: ItemType, operationType: OperationType) =
            manageItemUseCase
                .execute(ManageItemUseCase.ManageItemParam(
                    Item(itemId = selectedItemId ?: 0L,name = name, description = description, itemType = itemType),
                    operationType)
                ).catch {  }
                .onEach { res ->
                    res.onSuccess { item ->
                        if(operationType != OperationType.DELETE) {
                            selectedItemId = item.itemId
                            getSelectedItem()
                        }
                    }.onFailure { error ->

                    }
                    showSnackBack.value = operationType
                }
                .launchIn(viewModelScope)


    fun getSelectedItem() =
        selectedItemId?.let {
            getSelectedItemUseCase
                .execute(it)
                .catch {
                    Log.d("MARINERO", "getSelectedItem: $it")
                }
                .onEach { res ->
                    res.onSuccess { response ->
                        itemRelations.value = response.relations
                        state.value = response.item
                    }.onFailure { error ->
                        Log.d("MARINERO", "getSelectedItem: $error")
                    }
                }
                .launchIn(viewModelScope)
        }

    fun manageItemRelations(itemRelatedId: Long) =
        selectedItemId?.let {
            manageRelationUseCase
                .execute(ManageRelationUseCase.ManageRelationParam(
                    ItemRelation(it, itemRelatedId),
                    OperationType.CREATE
                )
                ).catch {  }
                .onEach { res ->
                }
                .launchIn(viewModelScope)
        }
}