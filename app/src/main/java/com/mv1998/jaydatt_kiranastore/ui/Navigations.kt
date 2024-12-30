package com.mv1998.jaydatt_kiranastore.ui

import kotlinx.serialization.Serializable


@Serializable
object ReportScreenRoute

@Serializable
data class ProductAddEditScreenRoute(val reportId : Int)

@Serializable
data class MergeReportScreenRoute(val reportId: Int)