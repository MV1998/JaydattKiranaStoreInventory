package com.mv1998.jaydatt_kiranastore.data.repository

import android.util.Log
import com.mv1998.jaydatt_kiranastore.data.local.dao.ReportDAO
import com.mv1998.jaydatt_kiranastore.data.local.entities.toReport
import com.mv1998.jaydatt_kiranastore.domain.models.Report
import com.mv1998.jaydatt_kiranastore.domain.repository.MergeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MergeRepositoryImpl @Inject constructor(private val reportDAO: ReportDAO) : MergeRepository {
    override fun getReportsExcludingById(reportId: Int): Flow<List<Report>> {
        Log.d("TAG", "getReportsExcludingById: $reportId")
        return reportDAO.getReportsExcludingById(reportId).map {
            it.map { reportEntity ->
                reportEntity.toReport()
            }
        }
    }

    override suspend fun mergeRepository(toReportId: Int, fromReportId: Int) {
        reportDAO.mergeRepository(toReportId, fromReportId)
    }

}