package com.mv1998.jaydatt_kiranastore.domain.usecase.product_usecase

import com.mv1998.jaydatt_kiranastore.domain.models.Product
import com.mv1998.jaydatt_kiranastore.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetAllProductUseCase @Inject constructor(private val productRepository: ProductRepository) {
    suspend fun execute(reportId: Int): Flow<List<Product>> {
        return productRepository.getAllProductBasedOnReportId(reportId)
    }
}