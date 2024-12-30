package com.mv1998.jaydatt_kiranastore.domain.repository

import com.mv1998.jaydatt_kiranastore.domain.models.Product
import kotlinx.coroutines.flow.Flow

interface ProductRepository {
    suspend fun insertProduct(product: Product)
    suspend fun updateProduct(product: Product)
    suspend fun upsertProduct(product: Product)
    suspend fun deleteProduct(product: Product)
    suspend fun deleteProductById(productId: Int)
    fun getAllProduct() : Flow<List<Product>>
    fun getAllProductBasedOnReportId(id : Int) : Flow<List<Product>>
}