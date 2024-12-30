package com.mv1998.jaydatt_kiranastore.domain.usecase.product_usecase

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CalculateTotalRevenueUseCase @Inject constructor(){
    fun execute(quantitySold : String, sellingCost : String) : Double {
        try {
            if (quantitySold.isEmpty() || sellingCost.isEmpty()) {
                return 0.0
            }
            return quantitySold.toDouble() * sellingCost.toDouble()
        } catch (e: NumberFormatException) {
            e.printStackTrace()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return  0.0
    }
}