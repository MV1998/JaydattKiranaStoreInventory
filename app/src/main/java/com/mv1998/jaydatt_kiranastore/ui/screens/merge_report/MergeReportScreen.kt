package com.mv1998.jaydatt_kiranastore.ui.screens.merge_report

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mv1998.jaydatt_kiranastore.ui.theme.headerTextStyle
import com.mv1998.jaydatt_kiranastore.utils.toCurrencyFormat
import com.mv1998.jaydatt_kiranastore.utils.toDateFormat


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MergeReportScreen(
    mergeScreenViewModel: MergeScreenViewModel,
    onNavigation: () -> Unit,
    modifier: Modifier = Modifier
) {

    val reportList by mergeScreenViewModel.reportList.collectAsStateWithLifecycle()

    Scaffold(
        containerColor = MaterialTheme.colorScheme.surface,
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = { onNavigation() }) {
                        Icon(
                            tint = MaterialTheme.colorScheme.onPrimaryContainer,
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = null
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                ),
                title = {
                    Text(
                        "Merge Report From", style = TextStyle(
                            fontSize = 20.sp,
                            color = MaterialTheme.colorScheme.onPrimaryContainer,
                            fontWeight = FontWeight.Bold
                        )
                    )
                }
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = modifier.padding(padding)
        ) {
            item {
                ListItem(
                    colors = ListItemDefaults.colors(
                        containerColor = MaterialTheme.colorScheme.surfaceContainerLow,
                    ),
                    headlineContent = {
                        Row(
                            modifier = modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                "Date", modifier = modifier.weight(1f),
                                style = headerTextStyle()
                            )
                            Text(
                                "Grand Revenue", modifier = modifier.weight(1f),
                                style = headerTextStyle()
                            )
                            Text(
                                "Grand Profit", modifier = modifier.weight(1f),
                                textAlign = TextAlign.Right,
                                style = headerTextStyle()
                            )
                        }
                    }
                )
            }
            items(reportList, key = { it.id }) {
                Card(
                    modifier = Modifier.padding(4.dp)
                ) {
                    ListItem(
                        modifier = modifier
                            .clickable {
                                mergeScreenViewModel.mergeReport(it.id)
                                onNavigation()
                            },
                        colors = ListItemDefaults.colors(
                            containerColor = MaterialTheme.colorScheme.surfaceContainerLow,
                        ),
                        headlineContent = {
                            Row(
                                modifier = modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    it.createdAt.toDateFormat(), style = TextStyle(
                                        color = MaterialTheme.colorScheme.onSurface,
                                        fontWeight = FontWeight.Bold
                                    )
                                )
                                Text(
                                    it.totalRevenue.toString().toCurrencyFormat(),
                                    style = TextStyle(
                                        color = MaterialTheme.colorScheme.onSurface,
                                        fontWeight = FontWeight.Bold
                                    )
                                )
                                Text(
                                    it.totalProfit.toString().toCurrencyFormat(), style = TextStyle(
                                        color = MaterialTheme.colorScheme.onSurface,
                                        fontWeight = FontWeight.Bold
                                    )
                                )
                            }
                        }
                    )
                }
            }
        }
    }
}