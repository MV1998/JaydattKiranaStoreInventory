package com.mv1998.jaydatt_kiranastore.domain.usecase.product_usecase

import com.mv1998.jaydatt_kiranastore.domain.models.Product
import com.mv1998.jaydatt_kiranastore.domain.repository.ProductRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UpdateProductUseCase @Inject constructor(private val productRepository: ProductRepository) {
    suspend fun execute(product: Product) {
        productRepository.updateProduct(product)
    }
}