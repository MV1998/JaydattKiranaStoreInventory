package com.mv1998.jaydatt_kiranastore.data.repository

import android.util.Log
import androidx.sqlite.db.SimpleSQLiteQuery
import com.mv1998.jaydatt_kiranastore.data.local.dao.ReportDAO
import com.mv1998.jaydatt_kiranastore.data.local.entities.toReport
import com.mv1998.jaydatt_kiranastore.domain.models.Report
import com.mv1998.jaydatt_kiranastore.domain.models.toReportEntity
import com.mv1998.jaydatt_kiranastore.domain.repository.ReportRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ReportRepositoryImpl @Inject constructor(
    private val reportDAO: ReportDAO,
    private val coroutineDispatcher: CoroutineDispatcher
) :
    ReportRepository {

    override suspend fun insertReport(report: Report) = withContext(coroutineDispatcher) {
        reportDAO.insertReport(report.toReportEntity())
    }

    override suspend fun updateReport(report: Report) = withContext(coroutineDispatcher) {
        reportDAO.updateReport(report.toReportEntity())
    }

    override suspend fun upsertReport(report: Report) = withContext(coroutineDispatcher) {
        reportDAO.upsertReport(report.toReportEntity())
    }

    override suspend fun deleteReport(report: Report) = withContext(coroutineDispatcher) {
        reportDAO.deleteReport(report.toReportEntity())
    }

    override suspend fun deleteReportById(reportId: Int) = withContext(coroutineDispatcher) {
        reportDAO.deleteReportById(reportId)
    }

    override suspend fun deleteProductByReportId(reportId: Int) = withContext(Dispatchers.IO) {
        reportDAO.deleteProductByReportId(reportId)
    }

    override suspend fun getAllReport(): Flow<List<Report>> = withContext(coroutineDispatcher) {
        return@withContext reportDAO.getAllReport().map {
            it.map { reportEntity ->
                reportEntity.toReport()
            }
        }
    }

    override suspend fun getReportBaseOnIsPurchase(isPurchase: Int): Flow<List<Report>> =
        withContext(coroutineDispatcher) {
            Log.d("TAG", "getReportBaseOnIsPurchase: ${Thread.currentThread().name} ")
            return@withContext reportDAO.getReportBaseOnIsPurchase(isPurchase).map {
                it.map { reportEntity ->
                    reportEntity.toReport()
                }
            }
        }

    override suspend fun getFilteredAndSortedReports(
        isDescending: Boolean,
        isPurchase: Int,
        targetColumn : String
    ): List<Report> = withContext(coroutineDispatcher) {
        val column = when(targetColumn) {
            "revenue" -> "grand_total_revenue"
            "profit" -> "grand_total_profit"
            else -> "createdAt"
        }
        var query = """
                     SELECT r.id as id, r.report_name, r.createdAt, r.modifiedAt,
                     r.modifiedBy, SUM(p.product_total_revenue) as grand_total_revenue,
                     SUM(p.product_total_profit) as grand_total_profit, r.month as month, r.purchase as purchase
                     FROM reports r LEFT JOIN products p ON p.report_id = r.id WHERE purchase = $isPurchase GROUP BY r.id ORDER BY $column
                """.trimIndent()

        val orderDirection = if (isDescending) "DESC" else "ASC"
        query += " $orderDirection"

        Log.d("TAG", "getFilteredAndSortedReports: $query")
        val supportSQLiteQuery = SimpleSQLiteQuery(query)
        return@withContext reportDAO.getSortedFilteredReports(supportSQLiteQuery).map { it.toReport() }
    }


}