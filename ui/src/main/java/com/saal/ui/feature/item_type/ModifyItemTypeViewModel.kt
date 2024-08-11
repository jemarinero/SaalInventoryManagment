package com.saal.ui.feature.item_type

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.saal.domain.model.ItemType
import com.saal.domain.model.OperationType
import com.saal.domain.usecase.ManageItemTypeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import javax.inject.Inject

@HiltViewModel
class ModifyItemTypeViewModel
@Inject
constructor(
    private val manageItemTypeUseCase: ManageItemTypeUseCase
): ViewModel() {

    fun createItemType(name: String) {
        manageItemTypeUseCase
            .execute(ManageItemTypeUseCase.ManageItemTypeParam(ItemType(0L, name), OperationType.CREATE))
            .launchIn(viewModelScope)

    }
}