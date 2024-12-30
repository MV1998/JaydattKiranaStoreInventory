package com.mv1998.jaydatt_kiranastore.domain.repository

import com.mv1998.jaydatt_kiranastore.domain.models.Report
import kotlinx.coroutines.flow.Flow

interface MergeRepository {
    fun getReportsExcludingById(reportId : Int) : Flow<List<Report>>
    suspend fun mergeRepository(toReportId: Int, fromReportId : Int)
}