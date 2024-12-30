package com.mv1998.jaydatt_kiranastore.ui.screens.productaddedit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mv1998.jaydatt_kiranastore.domain.models.Product
import com.mv1998.jaydatt_kiranastore.domain.models.toProductUiState
import com.mv1998.jaydatt_kiranastore.domain.usecase.product_usecase.ProductUseCase
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel(assistedFactory = ProductAddEditViewModel.ProductAddEditViewModelFactory::class)
class ProductAddEditViewModel @AssistedInject constructor(
    private val productUseCase: ProductUseCase,
    @Assisted private val reportId: Int
) : ViewModel() {

    private var _productAddEditUiState =
        MutableStateFlow(ProductAddEditUiState(reportId = reportId))
    val productAddEditUiState = _productAddEditUiState.asStateFlow()

    private var _productList = MutableStateFlow(mutableListOf<Product>())
    val productList = _productList.asStateFlow()

    private var _shouldShowDialog = MutableStateFlow(false)
    val shouldShowDialog = _shouldShowDialog.asStateFlow()

    private var selectedProduct: Product? = null

    @AssistedFactory
    interface ProductAddEditViewModelFactory {
        fun create(reportId: Int): ProductAddEditViewModel
    }

    init {
        fetchList()
    }

    fun name(newValue: String) {
        _productAddEditUiState.update { currentState ->
            currentState.copy(
                name = newValue
            )
        }
    }

    fun purchaseCost(newValue: String) {
        _productAddEditUiState.update { currentState ->
            currentState.copy(purchaseCost = newValue)
        }
    }

    fun sellingCost(newValue: String) {
        _productAddEditUiState.update { currentState ->
            currentState.copy(sellingCost = newValue)
        }
    }

    fun quantitySold(newValue: String) {
        _productAddEditUiState.update { currentState ->
            currentState.copy(quantitySold = newValue)
        }
    }

    fun transport(newValue: String) {
        _productAddEditUiState.update { currentState ->
            currentState.copy(transportCost = newValue)
        }
    }

    fun totalRevenue() {
        _productAddEditUiState.update { currentState ->
            currentState.copy(
                totalRevenue = productUseCase.calculateTotalRevenueUseCase
                    .execute(
                        quantitySold = _productAddEditUiState.value.quantitySold,
                        sellingCost = _productAddEditUiState.value.sellingCost
                    ).toString()
            )
        }
        calculateTotalProfit()
    }

    fun onButtonEventClick() {
        var product = _productAddEditUiState.value.toProduct().copy(reportId = reportId)
        viewModelScope.launch {
            if (selectedProduct == null) {
                if (product.name.isEmpty()) return@launch
                productUseCase.addProductUseCase.execute(product)
            } else {
                product = product.copy(id = selectedProduct!!.id)
                productUseCase.updateProductUseCase.execute(product)
            }
        }
        onDialogClose()
    }

    fun onUpdateItemClick(product: Product) {
        _productAddEditUiState.value = product.toProductUiState()
        selectedProduct = product
        updateProductAction(ProductAction.UPDATE)
        onDialogOpen(ProductAction.UPDATE)
    }

    fun onDeleteItemClick(productId: Int) {
        viewModelScope.launch {
            productUseCase.deleteProductUseCase.execute(productId)
        }
    }

    fun onDialogOpen(action: ProductAction) {
        if (action == ProductAction.ADD) {
            _productAddEditUiState.value = ProductAddEditUiState()
            selectedProduct = null
            updateProductAction(action)
        }
        _shouldShowDialog.value = true
    }

    fun onDialogClose() {
        _shouldShowDialog.value = false
    }

    private fun calculateTotalProfit() {
        val uiState = _productAddEditUiState.value.toProduct()
        _productAddEditUiState.update { currentState ->
            currentState.copy(
                totalProfit = productUseCase.calculateTotalProfitUseCase.execute(
                    totalRevenue = uiState.totalRevenue,
                    purchaseCost = uiState.purchaseCost,
                    transportCost = uiState.transportCost,
                    quantitySold = uiState.quantitySold
                ).toString()
            )
        }
    }

    fun grandTotalRevenue(): Float {
        return _productList.value.sumOf { it.totalRevenue }.toFloat()
    }

    fun grandTotalProfit(): Float {
        return _productList.value.sumOf { it.totalProfit }.toFloat()
    }

    private fun updateProductAction(action: ProductAction) {
        _productAddEditUiState.update { currentState ->
            currentState.copy(productAction = action)
        }
    }

    private fun fetchList() {
        viewModelScope.launch {
            productUseCase.getAllProductUseCase.execute(reportId).collect {
                _productList.value = it.toMutableList()
            }
        }
    }
}
