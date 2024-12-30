package com.mv1998.jaydatt_kiranastore.ui.screens.reports

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mv1998.jaydatt_kiranastore.domain.models.Report
import com.mv1998.jaydatt_kiranastore.domain.repository.ReportRepository
import com.mv1998.jaydatt_kiranastore.utils.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Calendar
import java.util.Date
import javax.inject.Inject

sealed class ReportState {
    data object Loading : ReportState()
    data class Success(val report: List<Report>) : ReportState()
    data class Error(val error: String) : ReportState()
}

data class FilterSortingUiState(
    val isFilterAndSortingApplied : Boolean = false,
    val isDescending: Boolean = false,
    val targetColumn: String = "createdAt",
    val isPurchase : Int = 1
)

@HiltViewModel
class ReportViewModel @Inject constructor(private val reportRepository: ReportRepository) :
    ViewModel() {

    private val _reportState = MutableStateFlow<ReportState>(ReportState.Loading)
    val reportState = _reportState.asStateFlow()

    private val _bottomBarState = MutableStateFlow(Constants.purchaseColumnValue)
    val bottomBarState = _bottomBarState.asStateFlow()

    private val _filterSortingUiState = MutableStateFlow(FilterSortingUiState())
    val filterSortingUiState = _filterSortingUiState.asStateFlow()

    val calendar = Calendar.getInstance()

    init {
        fetchReports()
    }

    fun fetchReports() {
        viewModelScope.launch {
            _reportState.value = ReportState.Loading
            try {
                if (!filterSortingUiState.value.isFilterAndSortingApplied) {
                    reportRepository.getReportBaseOnIsPurchase(bottomBarState.value).collect {
                        _reportState.value = ReportState.Success(
                            it.toMutableList()
                        )
                    }
                } else {
                    val report = reportRepository.getFilteredAndSortedReports(
                        filterSortingUiState.value.isDescending
                        ,filterSortingUiState.value.isPurchase,
                        filterSortingUiState.value.targetColumn).toMutableList()
                    _reportState.value = ReportState.Success(
                        report.toMutableList()
                    )
                }
            } catch (e: Exception) {
                _reportState.value = ReportState.Error("Couldn't fetch the reports.")
            }
        }
    }

    fun addNewReport(date: Date?) {
        date?.let {
            calendar.time = it
            viewModelScope.launch {
                try {
                    reportRepository.insertReport(
                        Report(
                            createdAt = date,
                            modifiedAt = date,
                            totalProfit = 0.0,
                            totalRevenue = 0.0,
                            isPurchase = bottomBarState.value,
                            month = calendar.get(Calendar.MONTH)
                        )
                    )
                     fetchReports()
                } catch (e: Exception) {
                    _reportState.value = ReportState.Error("Couldn't add the report.")
                }
            }
        }
    }

    fun deleteReport(reportId: Int) {
        viewModelScope.launch {
            try {
                reportRepository.deleteProductByReportId(reportId = reportId)
                reportRepository.deleteReportById(reportId = reportId)
                  fetchReports()
            } catch (e: Exception) {
                _reportState.value = ReportState.Error("Couldn't delete the report.")
            }
        }
    }

    fun onBottomBarStateChange(value: Int) {
        if (_bottomBarState.value == value) return
        _bottomBarState.value = value
        _filterSortingUiState.update { currentFilterSortingUiState ->
            currentFilterSortingUiState.copy(
                isPurchase = _bottomBarState.value,
            )
        }
        fetchReports()
    }

    fun onApplyFilterAndSorting(value: Boolean, targetColumn: String, isDescending: Boolean) {
        _filterSortingUiState.update { currentFilterSortingUiState ->
            currentFilterSortingUiState.copy(
                isFilterAndSortingApplied = value,
                targetColumn = targetColumn,
                isPurchase = _bottomBarState.value,
                isDescending = isDescending
            )
        }
        fetchReports()
    }
}