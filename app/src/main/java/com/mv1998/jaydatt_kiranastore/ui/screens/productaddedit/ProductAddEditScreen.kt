package com.mv1998.jaydatt_kiranastore.ui.screens.productaddedit

import androidx.activity.OnBackPressedCallback
import androidx.activity.OnBackPressedDispatcher
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.MergeType
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mv1998.jaydatt_kiranastore.domain.models.Product
import com.mv1998.jaydatt_kiranastore.ui.theme.headerTextStyle
import com.mv1998.jaydatt_kiranastore.utils.capitalizeFirstLetter
import com.mv1998.jaydatt_kiranastore.utils.toCurrencyFormat

enum class ProductAction {
    ADD, UPDATE
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductAddEditScreen(
    productAddEditViewModel: ProductAddEditViewModel,
    reportId: Int,
    onNavigation: (Int) -> Unit,
    onNavigateToMergeScreen: (Int) -> Unit,
    modifier: Modifier = Modifier,
    backPressedDispatcher: OnBackPressedDispatcher? =
        LocalOnBackPressedDispatcherOwner.current?.onBackPressedDispatcher,
) {
    val productUiState by productAddEditViewModel.productAddEditUiState.collectAsStateWithLifecycle()
    val productList by productAddEditViewModel.productList.collectAsStateWithLifecycle()
    val shouldShowDialog by productAddEditViewModel.shouldShowDialog.collectAsStateWithLifecycle()
    val currentOnBackPressed by rememberUpdatedState(newValue = onNavigation)

    val backCallback = remember {
        object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                currentOnBackPressed(reportId)
            }
        }
    }

    DisposableEffect(key1 = backPressedDispatcher) {
        backPressedDispatcher?.addCallback(backCallback)
        onDispose {
            backCallback.remove()
        }
    }

    LaunchedEffect(
        productUiState.sellingCost, productUiState.quantitySold,
        productUiState.transportCost, productUiState.purchaseCost
    ) {
        productAddEditViewModel.totalRevenue()
    }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.surface,
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                ),
                navigationIcon = {
                    IconButton(onClick = { onNavigation(reportId) }) {
                        Icon(
                            tint = MaterialTheme.colorScheme.onPrimaryContainer,
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = null
                        )
                    }
                },
                actions = {
                    IconButton(onClick = {
                        onNavigateToMergeScreen(reportId)
                    }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.MergeType,
                            contentDescription = "Merge",
                            tint = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    }
                },
                title = {
                    Text(
                        "Products",
                        style = TextStyle(
                            fontSize = 20.sp,
                            color = MaterialTheme.colorScheme.onPrimaryContainer,
                            fontWeight = FontWeight.Bold
                        )
                    )
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                productAddEditViewModel.onDialogOpen(ProductAction.ADD)
            }) {
                Icon(imageVector = Icons.Filled.Add, contentDescription = null)
            }
        }
    ) { padding ->
        if (productList.isEmpty()) {
            Column(
                modifier = modifier
                    .padding(padding)
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    "No Product", style = TextStyle(
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                )
                Text("Add Product by + Icon")
            }
        } else {
            Column(
                modifier = modifier.padding(padding)
            ) {
                ListItem(
                    headlineContent = {
                        Text(
                            "Grand Total Revenue ${
                                productAddEditViewModel.grandTotalRevenue()
                                    .toString().toCurrencyFormat()
                            }"
                        )
                    },
                    supportingContent = {
                        Text(
                            "Grand Total Income ${
                                productAddEditViewModel.grandTotalProfit()
                                    .toString().toCurrencyFormat()
                            }"
                        )
                    }
                )
                ListItem(
                    colors = ListItemDefaults.colors(
                        containerColor = MaterialTheme.colorScheme.surfaceContainerLow,
                    ),
                    headlineContent = {
                        Row(
                            modifier = modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                "Name", modifier = modifier.weight(1f),
                                style = headerTextStyle()
                            )
                            Text(
                                "Total Revenue", modifier = modifier.weight(1f),
                                style = headerTextStyle()
                            )
                            Text(
                                "Total Profit", modifier = modifier.weight(1f),
                                textAlign = TextAlign.Right,
                                style = headerTextStyle()
                            )
                        }
                    }
                )
                ProductListLayout(
                    productList,
                    onUpdate = productAddEditViewModel::onUpdateItemClick,
                    onDelete = productAddEditViewModel::onDeleteItemClick
                )
            }
        }
    }

    if (shouldShowDialog) {
        BasicAlertDialog(
            modifier = modifier
                .fillMaxWidth(1f)
                .wrapContentHeight()
                .background(color = Color.White, shape = RoundedCornerShape(10.dp)),
            onDismissRequest = {
                productAddEditViewModel.onDialogClose()
            }
        ) {
            ProductAddEditLayout(
                productUiState = productUiState,
                onNameChange = productAddEditViewModel::name,
                onPurchaseCostChange = productAddEditViewModel::purchaseCost,
                onSellingCostChange = productAddEditViewModel::sellingCost,
                onQuantityChange = productAddEditViewModel::quantitySold,
                onTransportChange = productAddEditViewModel::transport,
                onAddUpdateButtonClick = productAddEditViewModel::onButtonEventClick
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ProductListLayout(
    productList: MutableList<Product>,
    onUpdate: (Product) -> Unit,
    onDelete: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    var showDeleteDialog by remember {
        mutableIntStateOf(-1)
    }
    LazyColumn(
        modifier = modifier
    ) {
        itemsIndexed(productList, key = { _, item -> item.id }) { _, it ->
            Card(
                modifier = modifier.padding(4.dp)
            ) {
                ListItem(
                    colors = ListItemDefaults.colors(
                        containerColor = MaterialTheme.colorScheme.surfaceContainerLow,
                    ),
                    modifier = Modifier
                        .combinedClickable(
                            onClick = { onUpdate(it) },
                            onLongClick = {
                                showDeleteDialog = it.id
                            }
                        ),
                    headlineContent = {
                        Row(
                            modifier = modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                it.name.capitalizeFirstLetter(),
                                modifier = modifier.weight(1f),
                                style = TextStyle(
                                    color = MaterialTheme.colorScheme.onSurface,
                                    fontWeight = FontWeight.Bold
                                )
                            )
                            Text(
                                it.totalRevenue.toString().toCurrencyFormat(),
                                modifier = modifier.weight(1f),
                                textAlign = TextAlign.Center, style = TextStyle(
                                    color = MaterialTheme.colorScheme.onSurface,
                                    fontWeight = FontWeight.Bold
                                )
                            )
                            Text(
                                it.totalProfit.toString().toCurrencyFormat(),
                                modifier = modifier.weight(1f),
                                textAlign = TextAlign.Right, style = TextStyle(
                                    color = MaterialTheme.colorScheme.onSurface,
                                    fontWeight = FontWeight.Bold
                                )
                            )
                        }
                    }
                )
            }
        }
    }
    if (showDeleteDialog != -1) {
        Dialog(
            onDismissRequest = {
                showDeleteDialog = -1
            }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth(.9f)
                    .wrapContentHeight()
                    .background(
                        color = MaterialTheme.colorScheme.surface,
                        shape = RoundedCornerShape(5.dp)
                    )
                    .padding(10.dp)
            ) {
                Text(
                    "Do you want to delete selected product?",
                    style = TextStyle(
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 20.dp),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(onClick = {
                        showDeleteDialog = -1
                    }) { Text("Cancel") }

                    Button(onClick = {
                        onDelete(showDeleteDialog)
                        showDeleteDialog = -1
                    }) {
                        Text("Delete")
                    }
                }
            }
        }
    }
}

@Composable
fun ProductAddEditLayout(
    productUiState: ProductAddEditUiState,
    onNameChange: (String) -> Unit,
    onPurchaseCostChange: (String) -> Unit,
    onSellingCostChange: (String) -> Unit,
    onQuantityChange: (String) -> Unit,
    onTransportChange: (String) -> Unit,
    onAddUpdateButtonClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .padding(10.dp)
            .verticalScroll(scrollState)
    ) {
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = productUiState.name,
            onValueChange = onNameChange,
            label = {
                Text("Product Name")
            },
            singleLine = true,
            keyboardOptions = KeyboardOptions.Default.copy(
                autoCorrectEnabled = true,
                imeAction = ImeAction.Next,
                keyboardType = KeyboardType.Text,
            )
        )

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = productUiState.purchaseCost,
            onValueChange = onPurchaseCostChange,
            label = {
                Text("Purchase Cost")
            },
            singleLine = true,
            keyboardOptions = KeyboardOptions.Default.copy(
                autoCorrectEnabled = true,
                imeAction = ImeAction.Next,
                keyboardType = KeyboardType.Number
            )
        )

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = productUiState.sellingCost,
            onValueChange = onSellingCostChange,
            label = {
                Text("Selling Cost")
            },
            singleLine = true,
            keyboardOptions = KeyboardOptions.Default.copy(
                autoCorrectEnabled = true,
                imeAction = ImeAction.Next,
                keyboardType = KeyboardType.Number
            )
        )


        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = productUiState.quantitySold,
            onValueChange = onQuantityChange,
            label = {
                Text("Quantity Sold")
            },
            singleLine = true,
            keyboardOptions = KeyboardOptions.Default.copy(
                autoCorrectEnabled = true,
                imeAction = ImeAction.Next,
                keyboardType = KeyboardType.Number
            )
        )

        ListItem(
            modifier = Modifier.padding(top = 10.dp),
            headlineContent = {
                Text(
                    "Total Revenue ${
                        if (productUiState.totalRevenue.isNotEmpty())
                            "%.2f".format(productUiState.totalRevenue.toFloat()).toCurrencyFormat()
                        else "--"
                    }"
                )
            }
        )

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = productUiState.transportCost,
            onValueChange = onTransportChange,
            label = {
                Text("Transport Cost")
            },
            singleLine = true,
            keyboardOptions = KeyboardOptions.Default.copy(
                autoCorrectEnabled = true,
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Number,
            ),
            keyboardActions = KeyboardActions {
                onAddUpdateButtonClick()
            }
        )

        ListItem(
            modifier = Modifier.padding(top = 10.dp),
            headlineContent = {
                Text(
                    "Total Profit ${
                        if (productUiState.totalProfit.isNotEmpty())
                            "%.2f".format(productUiState.totalProfit.toFloat()).toCurrencyFormat()
                        else "--"
                    }"
                )
            }
        )

        Button(
            onClick = onAddUpdateButtonClick,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp)
        ) {
            Text(if (productUiState.productAction == ProductAction.ADD) "Add" else "Update")
        }
    }
}


//@Preview
//@Composable
//private fun ProductAddEditLayoutPreview() {
//    ProductAddEditLayout(
//        name = "",
//        costPerItem = 0.0,
//        totalSold = 0.0,
//        totalRevenue = "",
//        onNameUpdate = {},
//        onCostPerItemUpdate = {},
//        onTotalSoldUpdate = {},
//        onAddButton = {})
//}