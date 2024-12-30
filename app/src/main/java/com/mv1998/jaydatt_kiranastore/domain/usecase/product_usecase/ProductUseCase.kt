package com.mv1998.jaydatt_kiranastore.domain.usecase.product_usecase

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProductUseCase @Inject constructor(
    val addProductUseCase: AddProductUseCase,
    val updateProductUseCase: UpdateProductUseCase,
    val deleteProductUseCase: DeleteProductUseCase,
    val getAllProductUseCase: GetAllProductUseCase,
    val calculateTotalProfitUseCase: CalculateTotalProfitUseCase,
    val calculateTotalRevenueUseCase: CalculateTotalRevenueUseCase
)