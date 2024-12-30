package com.mv1998.jaydatt_kiranastore

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.mv1998.jaydatt_kiranastore.ui.InventoryNavHost
import com.mv1998.jaydatt_kiranastore.ui.theme.JaydattKiranaStoreInventoryTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            JaydattKiranaStoreInventoryTheme(
                dynamicColor = false
            ) {
                InventoryApp()
            }
        }
    }
}

@Composable
fun InventoryApp(modifier: Modifier = Modifier) {
    InventoryNavHost()
}
