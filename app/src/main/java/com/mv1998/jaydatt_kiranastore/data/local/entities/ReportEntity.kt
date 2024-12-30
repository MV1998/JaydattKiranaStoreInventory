package com.mv1998.jaydatt_kiranastore.data.local.entities

import android.icu.util.Calendar
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mv1998.jaydatt_kiranastore.domain.models.Report
import java.util.Date

@Entity(tableName = "reports")
data class ReportEntity(
    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @ColumnInfo(name = "report_name")
    val name: String = "",

    @ColumnInfo(name = "createdAt")
    val createdAt: Date = Date(),

    @ColumnInfo(name = "modifiedAt")
    val modifiedAt : Date = Date(),

    @ColumnInfo(name = "modifiedBy")
    val modifiedBy : String = "",

    @ColumnInfo(name = "grand_total_revenue")
    var grandTotalRevenue : Double = 0.0,

    @ColumnInfo(name = "grand_total_profit")
    var grandTotalProfit : Double = 0.0,

    @ColumnInfo(name = "month")
    val month : Int,

    @ColumnInfo(name = "purchase")
    val isPurchase: Int = 0
)

fun ReportEntity.toReport() : Report {
    return Report(
        id = this.id,
        name = this.name,
        createdAt = this.createdAt,
        modifiedAt = this.modifiedAt,
        modifiedBy = this.modifiedBy,
        totalRevenue = this.grandTotalRevenue,
        totalProfit = this.grandTotalProfit,
        month = this.month,
        isPurchase = this.isPurchase,
    )
}