package com.mv1998.jaydatt_kiranastore.domain.usecase.product_usecase

import com.mv1998.jaydatt_kiranastore.domain.repository.ProductRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DeleteProductUseCase @Inject constructor(private val productRepository: ProductRepository) {
    suspend fun execute(productId: Int) {
        productRepository.deleteProductById(productId = productId)
    }
}