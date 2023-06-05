package dev.toke.ameplus.screens.main

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import dev.toke.ameplus.navigation.AMEPlusScreens

@Composable
fun AMEPlusMainScreen(navController: NavController) {
    Text("Main Screen", style = MaterialTheme.typography.headlineLarge)
    Button(modifier = Modifier.padding(6.dp), onClick = {
        navController.navigate(AMEPlusScreens.WarehouseScanAndMatchScreen.name)
    }) {
        Text(text = "Warehouse")
    }
}