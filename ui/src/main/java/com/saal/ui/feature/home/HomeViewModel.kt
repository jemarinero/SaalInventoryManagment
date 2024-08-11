package com.saal.ui.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.saal.domain.common.error.Failure
import com.saal.domain.common.result.onFailure
import com.saal.domain.common.result.onSuccess
import com.saal.domain.model.Item
import com.saal.domain.model.OperationType
import com.saal.domain.usecase.ManageItemUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

@HiltViewModel
class HomeViewModel
@Inject
constructor(): ViewModel()