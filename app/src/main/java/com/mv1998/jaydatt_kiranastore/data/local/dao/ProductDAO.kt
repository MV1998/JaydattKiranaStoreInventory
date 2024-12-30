package com.mv1998.jaydatt_kiranastore.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert
import com.mv1998.jaydatt_kiranastore.data.local.entities.ProductEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDAO {

    @Insert
    suspend fun insertProduct(product : ProductEntity)

    @Upsert
    suspend fun upsertProduct(product : ProductEntity)

    @Update
    suspend fun updateProduct(product: ProductEntity)

    @Delete
    suspend fun deleteProduct(product: ProductEntity)

    @Query("DELETE FROM products WHERE product_id = :id")
    suspend fun deleteProductById(id : Int)

    @Query("SELECT * FROM products")
    fun getAllProduct() : Flow<List<ProductEntity>>

    @Query("SELECT * FROM products WHERE report_id = :id")
    fun getAllProductsBasedOnReportId(id : Int) : Flow<List<ProductEntity>>



}