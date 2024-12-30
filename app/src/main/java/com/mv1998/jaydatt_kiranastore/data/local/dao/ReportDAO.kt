package com.mv1998.jaydatt_kiranastore.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.RawQuery
import androidx.room.Update
import androidx.room.Upsert
import androidx.sqlite.db.SupportSQLiteQuery
import com.mv1998.jaydatt_kiranastore.data.local.entities.ReportEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ReportDAO {

    @Insert
    suspend fun insertReport(report : ReportEntity)

    @Upsert
    suspend fun upsertReport(report : ReportEntity)

    @Update
    suspend fun updateReport(report: ReportEntity)

    @Delete
    suspend fun deleteReport(report : ReportEntity)

    @Query("DELETE FROM reports WHERE id = :reportId")
    suspend fun deleteReportById(reportId : Int)

    @Query("DELETE FROM products WHERE report_id = :reportId")
    suspend fun deleteProductByReportId(reportId : Int)

    @Query("SELECT SUM(product_total_revenue) FROM products WHERE report_id = :reportId")
    suspend fun getGrandRevenue(reportId: Int) : Double

    @Query("SELECT r.id as id, r.report_name, r.createdAt, r.modifiedAt, r.modifiedBy, SUM(product_total_revenue) as grand_total_revenue, SUM(product_total_profit) as grand_total_profit, r.month as month, r.purchase as purchase FROM reports r LEFT JOIN products p ON p.report_id = r.id GROUP By r.id")
    //@Query("Select * FROM reports")
    fun getAllReport() : Flow<List<ReportEntity>>

   // @Query("Select * FROM reports WHERE id != :reportId")
    @Query("SELECT r.id as id, r.report_name, r.createdAt, r.modifiedAt, r.modifiedBy, SUM(product_total_revenue) as grand_total_revenue, SUM(product_total_profit) as grand_total_profit, r.month as month, r.purchase as purchase FROM reports r LEFT JOIN products p ON p.report_id = r.id WHERE r.id != :reportId GROUP By r.id")
    fun getReportsExcludingById(reportId : Int) : Flow<List<ReportEntity>>

    @Query("Update products SET report_id = :toReportId WHERE report_id = :fromReportId")
    suspend fun mergeRepository(toReportId : Int, fromReportId : Int)

    @Query("Update reports SET month = :month WHERE id = :id")
    suspend fun updateMonthInReport(month: Int, id : Int)

    @Query("SELECT r.id as id, r.report_name, r.createdAt, r.modifiedAt, r.modifiedBy, SUM(product_total_revenue) as grand_total_revenue, SUM(product_total_profit) as grand_total_profit, r.month as month, r.purchase as purchase FROM reports r LEFT JOIN products p ON p.report_id = r.id WHERE purchase = :isPurchase GROUP By r.id ORDER BY r.createdAt DESC")
    fun getReportBaseOnIsPurchase(isPurchase : Int) : Flow<List<ReportEntity>>

//    @Query("SELECT r.id as id, r.report_name, r.createdAt, r.modifiedAt, r.modifiedBy, SUM(product_total_revenue) as grand_total_revenue, SUM(product_total_profit) as grand_total_profit, r.month as month, r.purchase as purchase FROM reports r LEFT JOIN products p ON p.report_id = r.id WHERE purchase = :isPurchase GROUP By r.id ORDER BY r.createdAt ASC")
//    fun getSortedReportAscending(isPurchase : Int) : Flow<List<ReportEntity>>
//
//    @Query("SELECT r.id as id, r.report_name, r.createdAt, r.modifiedAt, r.modifiedBy, SUM(product_total_revenue) as grand_total_revenue, SUM(product_total_profit) as grand_total_profit, r.month as month, r.purchase as purchase FROM reports r LEFT JOIN products p ON p.report_id = r.id WHERE purchase = :isPurchase GROUP By r.id ORDER BY r.grand_total_revenue DESC")
//    fun getSortedReportDescendingByRevenue(isPurchase : Int) : Flow<List<ReportEntity>>
//
//    @Query("SELECT r.id as id, r.report_name, r.createdAt, r.modifiedAt, r.modifiedBy, SUM(product_total_revenue) as grand_total_revenue, SUM(product_total_profit) as grand_total_profit, r.month as month, r.purchase as purchase FROM reports r LEFT JOIN products p ON p.report_id = r.id WHERE purchase = :isPurchase GROUP By r.id ORDER BY r.grand_total_profit DESC")
//    fun getSortedReportDescendingByProfit(isPurchase : Int) : Flow<List<ReportEntity>>

    @RawQuery
    suspend fun getSortedFilteredReports(query : SupportSQLiteQuery) : List<ReportEntity>
}