package com.mv1998.jaydatt_kiranastore.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mv1998.jaydatt_kiranastore.domain.models.Product

@Entity(tableName = "products")
data class ProductEntity(
    @ColumnInfo(name = "product_id")
    @PrimaryKey(autoGenerate = true)
    val id : Int = 0,

    @ColumnInfo(name = "report_id")
    val reportId : Int = 0,

    @ColumnInfo(name = "product_name")
    val name: String = "",

    @ColumnInfo(name = "product_purchase_cost")
    val purchaseCost: Double = 0.0,

    @ColumnInfo(name = "product_selling_cost")
    val sellingCost: Double = 0.0,

    @ColumnInfo(name = "product_transfer_cost")
    val transportCost: Double = 0.0,

    @ColumnInfo(name = "product_quantity_sold")
    val quantitySold: Double = 0.0,

    @ColumnInfo(name = "product_total_revenue")
    val totalRevenue: Double = 0.0,

    @ColumnInfo(name = "product_total_profit")
    val totalProfit: Double = 0.0
)

fun ProductEntity.toProduct() : Product {
    return Product(
        name = this.name,
        id = this.id,
        reportId = this.reportId,
        purchaseCost = this.purchaseCost,
        sellingCost = this.sellingCost,
        transportCost = this.transportCost,
        quantitySold = this.quantitySold,
        totalRevenue = this.totalRevenue,
        totalProfit = this.totalProfit
    )
}
