package com.mv1998.jaydatt_kiranastore.domain.models

import com.mv1998.jaydatt_kiranastore.data.local.entities.ReportEntity
import java.util.Date

data class Report(
    val id: Int = 0,
    val name: String = "REPORT",
    val createdAt: Date = Date(),
    val modifiedAt : Date = Date(),
    val modifiedBy : String = "Mobile User",
    val totalRevenue : Double,
    val totalProfit : Double,
    val month : Int,
    val isPurchase : Int = 1
)

fun Report.toReportEntity() : ReportEntity {
    return ReportEntity(
        id = this.id,
        name = this.name,
        createdAt = this.createdAt,
        modifiedAt = this.modifiedAt,
        modifiedBy = this.modifiedBy,
        grandTotalRevenue = this.totalRevenue,
        grandTotalProfit = this.totalProfit,
        month = this.month,
        isPurchase = this.isPurchase
    )
}