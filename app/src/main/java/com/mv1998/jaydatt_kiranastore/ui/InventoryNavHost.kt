package com.mv1998.jaydatt_kiranastore.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.mv1998.jaydatt_kiranastore.ui.screens.merge_report.MergeReportScreen
import com.mv1998.jaydatt_kiranastore.ui.screens.merge_report.MergeScreenViewModel
import com.mv1998.jaydatt_kiranastore.ui.screens.productaddedit.ProductAddEditScreen
import com.mv1998.jaydatt_kiranastore.ui.screens.productaddedit.ProductAddEditViewModel
import com.mv1998.jaydatt_kiranastore.ui.screens.reports.ReportScreen
import com.mv1998.jaydatt_kiranastore.ui.screens.reports.ReportViewModel

@Composable
fun InventoryNavHost(modifier: Modifier = Modifier) {

    val navController = rememberNavController()
    val reportViewModel = hiltViewModel<ReportViewModel>()

    NavHost(
        navController = navController,
        startDestination = ReportScreenRoute
    ) {
        composable<ReportScreenRoute> {
            ReportScreen(
                reportViewModel = reportViewModel,
                onNavigationToParticularReport = { id ->
                    navController.navigate(ProductAddEditScreenRoute(reportId = id))
                })
        }

        composable<ProductAddEditScreenRoute> { backStackEntry ->

            val productAddEditScreenRoute = backStackEntry.toRoute<ProductAddEditScreenRoute>()

            val productAddEditViewModel: ProductAddEditViewModel = hiltViewModel(
                creationCallback = { factory: ProductAddEditViewModel.ProductAddEditViewModelFactory ->
                    factory.create(productAddEditScreenRoute.reportId)
                }
            )

            ProductAddEditScreen(
                productAddEditViewModel = productAddEditViewModel,
                onNavigation = { id ->
                    reportViewModel.fetchReports()
//                    navController.popBackStack()
                    navController.navigate(ReportScreenRoute) {
                        popUpTo<ReportScreenRoute> {
                            inclusive = false
                        }
                    }
                },
                onNavigateToMergeScreen = { reportId ->
                    navController.navigate(MergeReportScreenRoute(reportId = reportId))
                },
                reportId = productAddEditScreenRoute.reportId,
//                onBackPress = {
//                    reportViewModel.fetchReports()
//                    navController.popBackStack()
//                }
            )
        }

        composable<MergeReportScreenRoute> { backStackEntry ->

            val mergeReportScreenRoute = backStackEntry.toRoute<MergeReportScreenRoute>()

            val mergeScreenViewModel : MergeScreenViewModel = hiltViewModel(
                creationCallback = { factory : MergeScreenViewModel.MergeScreenViewModelFactory ->
                    factory.create(mergeReportScreenRoute.reportId)
                }
            )

            MergeReportScreen(
                mergeScreenViewModel = mergeScreenViewModel,
                onNavigation = {
                    navController.popBackStack()
                }
            )
        }
    }
}