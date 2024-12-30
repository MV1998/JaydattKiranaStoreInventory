package com.mv1998.jaydatt_kiranastore.data.repository

import com.mv1998.jaydatt_kiranastore.data.local.dao.ProductDAO
import com.mv1998.jaydatt_kiranastore.data.local.entities.toProduct
import com.mv1998.jaydatt_kiranastore.domain.models.Product
import com.mv1998.jaydatt_kiranastore.domain.models.toProductEntity
import com.mv1998.jaydatt_kiranastore.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(private val productDAO: ProductDAO):
    ProductRepository {

    override suspend fun insertProduct(product: Product) {
        productDAO.insertProduct(product = product.toProductEntity())
    }

    override suspend fun updateProduct(product: Product) {
        productDAO.updateProduct(product = product.toProductEntity())
    }

    override suspend fun upsertProduct(product: Product) {
        productDAO.upsertProduct(product = product.toProductEntity())
    }

    override suspend fun deleteProduct(product: Product) {
        productDAO.deleteProduct(product.toProductEntity())
    }

    override suspend fun deleteProductById(productId: Int) {
        productDAO.deleteProductById(productId)
    }

    override fun getAllProduct(): Flow<List<Product>> {
        return productDAO.getAllProduct().map { it.map { productEntity -> productEntity.toProduct() } }
    }

    override fun getAllProductBasedOnReportId(id: Int): Flow<List<Product>> {
        return productDAO.getAllProductsBasedOnReportId(id).map { it.map { productEntity -> productEntity.toProduct() } }
    }
}