package com.saal.ui.feature.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.saal.ui.R
import com.saal.ui.components.ItemListComponent

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    onItemClick: (Long?) -> Unit
) {

    var isSearching by remember { mutableStateOf(false) }
    var searchText by remember { mutableStateOf("") }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            TopBar(
                title = stringResource(R.string.home_topbar_title),
                isSearching = isSearching,
                onSearchTextChange = {
                    searchText = it
                },
                onToggleSearch = {
                    isSearching = it
                },
                onMenuClick = {

                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    onItemClick(null)
                },
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_add),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {

            ItemListComponent(searchText = searchText
            ) {
                onItemClick(it)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    title: String,
    onSearchTextChange: (String) -> Unit,
    onToggleSearch: (Boolean) -> Unit,
    onMenuClick: (() -> Unit),
    isSearching: Boolean = false,
    onNavigateBack: (() -> Unit)? = null,
) {
    CenterAlignedTopAppBar(
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        modifier = Modifier.shadow(8.dp),
        title = {
            if (isSearching) {
                SearchBar(
                    hint = stringResource(R.string.home_topbar_search),
                    onToggleSearch = {
                        onToggleSearch(false)
                    },
                    onTextChange = onSearchTextChange
                )
            } else {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onSurface,
                    textAlign = TextAlign.Center,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

        },
        navigationIcon = {
            onNavigateBack?.let {
                IconButton(onClick = it) {
                    Icon(
                        modifier = Modifier.size(18.dp),
                        imageVector = ImageVector.vectorResource(id = R.drawable.ic_arrow_back),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }
        },
        actions = {
            Row {
                if (!isSearching) {
                    IconButton(
                        onClick = {
                            onToggleSearch.invoke(true)
                        }
                    ) {
                        Icon(
                            modifier = Modifier.size(18.dp),
                            imageVector = ImageVector.vectorResource(id = R.drawable.ic_search),
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }
                IconButton(onClick = onMenuClick) {
                    Icon(
                        modifier = Modifier.size(24.dp),
                        imageVector = ImageVector.vectorResource(id = R.drawable.ic_menu),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }
    )
}

@Composable
fun SearchBar(
    hint: String,
    modifier: Modifier = Modifier,
    isEnabled: (Boolean) = true,
    height: Dp = 40.dp,
    elevation: Dp = 3.dp,
    cornerShape: Shape = RoundedCornerShape(8.dp),
    backgroundColor: Color = MaterialTheme.colorScheme.background,
    onToggleSearch: () -> Unit = {},
    onTextChange: (String) -> Unit = {},
) {
    var text by remember { mutableStateOf(TextFieldValue()) }
    Row(
        modifier = Modifier
            .height(height)
            .fillMaxWidth()
            .shadow(elevation = elevation, shape = cornerShape)
            .background(color = backgroundColor, shape = cornerShape),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        BasicTextField(
            modifier = modifier
                .weight(5f)
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
            value = text,
            onValueChange = {
                text = it
                onTextChange(it.text)
            },
            enabled = isEnabled,
            textStyle = TextStyle(
                color = MaterialTheme.colorScheme.primary,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            ),
            decorationBox = { innerTextField ->
                if (text.text.isEmpty()) {
                    Text(
                        text = hint,
                        color = Color.Gray.copy(alpha = 0.5f),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                    )
                }
                innerTextField()
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Search
            ),
            keyboardActions = KeyboardActions(onSearch = { onToggleSearch() }),
            singleLine = true
        )
        Box(
            modifier = modifier
                .weight(1f)
                .size(40.dp)
                .background(color = Color.Transparent, shape = CircleShape)
                .clickable {
                    if (text.text.isNotEmpty()) {
                        text = TextFieldValue(text = "")
                        onTextChange("")
                    }
                },
        ) {
            if (text.text.isNotEmpty()) {
                Icon(
                    modifier = modifier
                        .fillMaxSize()
                        .padding(10.dp),
                    painter = painterResource(id = R.drawable.ic_clear),
                    contentDescription = stringResource(R.string.home_topbar_search),
                    tint = MaterialTheme.colorScheme.primary,
                )
            } else {
                Icon(
                    modifier = modifier
                        .fillMaxSize()
                        .padding(10.dp)
                        .clickable {
                            onToggleSearch()
                        },
                    painter = painterResource(id = R.drawable.ic_search),
                    contentDescription = stringResource(R.string.home_topbar_search),
                    tint = MaterialTheme.colorScheme.primary,
                )
            }
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun TopBarPreview() {
//    TopBar(
//        title = "Objects",
//        onActionClick = {}
//    )
//}