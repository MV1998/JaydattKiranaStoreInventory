package com.mv1998.jaydatt_kiranastore.domain.repository

import com.mv1998.jaydatt_kiranastore.domain.models.Report
import kotlinx.coroutines.flow.Flow

interface ReportRepository {
    suspend fun insertReport(report: Report)
    suspend fun updateReport(report: Report)
    suspend fun upsertReport(report: Report)
    suspend fun deleteReport(report: Report)
    suspend fun deleteReportById(reportId: Int)
    suspend fun deleteProductByReportId(reportId: Int)
    suspend fun getAllReport() : Flow<List<Report>>
    suspend fun getReportBaseOnIsPurchase(isPurchase : Int) : Flow<List<Report>>
    suspend fun getFilteredAndSortedReports(isDescending: Boolean,
                                            isPurchase: Int,
                                            targetColumn : String) : List<Report>
}