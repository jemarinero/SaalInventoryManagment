package com.saal.ui.feature.item_detail

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import androidx.hilt.navigation.compose.hiltViewModel
import com.saal.domain.model.ItemType
import com.saal.domain.model.OperationType
import com.saal.ui.R
import com.saal.ui.components.ItemListComponent
import com.saal.ui.components.ItemListDialog
import com.saal.ui.feature.item_type.ModifyItemTypeDialog

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ItemDetailScreen(
    itemId: Long? = null,
    viewModel: ItemDetailViewModel = hiltViewModel(),
    onNavigateBack: () -> Unit,
    onMenuClick: () -> Unit
) {
     itemId?.let {
         viewModel.selectedItemId = it
         viewModel.getSelectedItem()
    }
    var selectedIndex by remember { mutableIntStateOf(-1) }
    var showItemTypeDialog by remember { mutableStateOf(false) }
    var name by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var showDeleteButton by remember { mutableStateOf(false) }
    var snackBarVisibleState by remember { mutableStateOf(false) }
    var showRelationDialog by remember { mutableStateOf(false) }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                ),
                modifier = Modifier.shadow(8.dp),
                title = {
                    Text(
                        text = stringResource(R.string.detail_topbar_new_object),
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onSurface,
                        textAlign = TextAlign.Center,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            modifier = Modifier.size(18.dp),
                            imageVector = ImageVector.vectorResource(id = R.drawable.ic_arrow_back),
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                },
                actions = {
                    IconButton(onClick = onMenuClick) {
                        Icon(
                            modifier = Modifier.size(24.dp),
                            imageVector = ImageVector.vectorResource(id = R.drawable.ic_menu),
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            ItemTypeSelector(
                modifier = Modifier,
                itemList = viewModel.itemTypes.value,
                selectedIndex = selectedIndex,
                onNewTypeClick = {
                    showItemTypeDialog = true
                }
            ) {
                selectedIndex = it
            }

            Spacer(modifier = Modifier.size(8.dp))

            CardInformation(
                cardTitle = stringResource(R.string.detail_item_name)
            ) {
                TextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = name,
                    onValueChange = { newText ->
                        name = newText
                    },
                    singleLine = true
                )
            }

            Spacer(modifier = Modifier.size(8.dp))

            CardInformation(
                cardTitle = stringResource(R.string.detail_item_description)
            ) {
                TextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = description,
                    onValueChange = { newText ->
                        description = newText
                    }
                )
            }
            Spacer(modifier = Modifier.size(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                Button(
                    onClick = {
                        if(selectedIndex >= 0) {
                            viewModel.manageItem(
                                name,
                                description,
                                viewModel.itemTypes.value[selectedIndex],
                                OperationType.CREATE
                            )
                        }
                    }
                ) {
                    Text(
                        text = stringResource(R.string.generic_button_save),
                        textAlign = TextAlign.Center
                    )
                }
                if(showDeleteButton) {
                    Spacer(modifier = Modifier.size(8.dp))
                    Button(
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
                        onClick = {
                            viewModel.manageItem(
                                name,
                                description,
                                viewModel.itemTypes.value[selectedIndex],
                                OperationType.DELETE
                            )
                        }
                    ) {
                        Text(
                            text = stringResource(R.string.generic_button_delete),
                            style = MaterialTheme.typography.bodyMedium,
                            textAlign = TextAlign.Center
                        )
                    }
                }

            }

            Spacer(modifier = Modifier.size(8.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = stringResource(R.string.detail_item_relations),
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onSurface,
                    textAlign = TextAlign.Center,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.size(8.dp))
                if(showDeleteButton) {
                    Box(
                        modifier = Modifier
                            .padding(2.dp)
                            .clip(shape = RoundedCornerShape(4.dp))
                            .background(MaterialTheme.colorScheme.primary)
                            .clickable {
                                showRelationDialog = true
                            },
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_add),
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.size(8.dp))
            ItemListComponent(items = viewModel.itemRelations.value) {

            }

            if(snackBarVisibleState) {
                ShowSnackBack(message = stringResource(R.string.detail_item_update)) {
                    snackBarVisibleState = false
                }
            }
        }
    }

    if(showItemTypeDialog){
        ModifyItemTypeDialog(
            title = stringResource(R.string.dialog_item_type_new_item),
            onDismissRequest = { showItemTypeDialog = false },
        )
    }

    if (showRelationDialog) {
        ItemListDialog(
            onDismissRequest = {
                showRelationDialog = false
            }) {
            showRelationDialog = false
            viewModel.manageItemRelations(it)
        }
    }

    with(viewModel.showSnackBack.value) {
        when(this) {
            OperationType.DELETE -> onNavigateBack.invoke()
            OperationType.CREATE,
            OperationType.UPDATE -> {
                snackBarVisibleState = true
            }
            else -> Unit
        }
    }

    with(viewModel.state) {
        if(this.value != null) {
            selectedIndex = this.value?.itemType?.let { viewModel.itemTypes.value.indexOf(it) } ?: 0
            name = this.value?.name ?: ""
            description = this.value?.description ?: ""
            showDeleteButton = true
        }
    }

}

@Composable
fun ItemTypeSelector(
    modifier: Modifier,
    itemList: List<ItemType>,
    selectedIndex: Int,
    onNewTypeClick: () -> Unit,
    onItemClick: (Int) -> Unit
) {

    var showDropdown by rememberSaveable { mutableStateOf(false) }
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center) {

        CardInformation(
            cardTitle = stringResource(R.string.detail_type_title),
            onCardClick = { showDropdown = true },
        ) {
            Text(
                text = if(itemList.isNotEmpty() && selectedIndex >= 0) itemList[selectedIndex].name else stringResource(R.string.detail_placeholder_select_type),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurface,
                textAlign = TextAlign.Center,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }
        Box {
            if (showDropdown) {
                Popup(
                    alignment = Alignment.TopCenter,
                    properties = PopupProperties(
                        excludeFromSystemGesture = true,
                    ),
                    onDismissRequest = { showDropdown = false }
                ) {

                    Column(
                        modifier = modifier
                            .verticalScroll(state = scrollState)
                            .padding(horizontal = 24.dp, vertical = 8.dp)
                            .shadow(8.dp, shape = RoundedCornerShape(8.dp)),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {

                        itemList.onEachIndexed { index, item ->
                            if (index != 0) {
                                HorizontalDivider(thickness = 1.dp, color = MaterialTheme.colorScheme.background)
                            }
                            Box(
                                modifier = Modifier
                                    .background(MaterialTheme.colorScheme.surface)
                                    .fillMaxWidth()
                                    .padding(8.dp)
                                    .clickable {
                                        onItemClick(index)
                                        showDropdown = !showDropdown
                                    },
                                contentAlignment = Alignment.CenterStart
                            ) {
                                Text(text = item.name)
                            }
                        }
                        HorizontalDivider(thickness = 1.dp, color = MaterialTheme.colorScheme.background)
                        Row(
                            modifier = Modifier
                                .background(MaterialTheme.colorScheme.surface)
                                .fillMaxWidth()
                                .padding(8.dp)
                                .clickable {
                                    onNewTypeClick.invoke()
                                },
                            horizontalArrangement = Arrangement.Center
                        ) {

                            Icon(
                                imageVector = ImageVector.vectorResource(id = R.drawable.ic_add),
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary
                            )

                            Text(
                                text = stringResource(R.string.detail_type_button_add_new),
                                style = MaterialTheme.typography.titleMedium,
                                color = MaterialTheme.colorScheme.onSurface,
                                textAlign = TextAlign.Center,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    }
                }
            }
        }
    }

}

@Composable
fun ShowSnackBack(
    message: String,
    onDismiss: () -> Unit = {}
) {
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(snackbarHostState) {
        val result = snackbarHostState.showSnackbar(message, withDismissAction = true)
        when(result) {
            SnackbarResult.Dismissed,
            SnackbarResult.ActionPerformed -> onDismiss.invoke()
        }
    }

    SnackbarHost(
        hostState = snackbarHostState,
    )
}

@Composable
fun CardInformation(
    modifier: Modifier = Modifier,
    cardTitle: String,
    onCardClick: (() -> Unit)? = null,
    content: @Composable () -> Unit = {}
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onCardClick?.invoke() },
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(0.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = cardTitle,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface,
                textAlign = TextAlign.Center,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            content()
        }
    }
}