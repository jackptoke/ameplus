package dev.toke.ameplus.screens.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import dev.toke.ameplus.R
import dev.toke.ameplus.navigation.AMEPlusScreens

@Preview
@Composable
fun AMEPlusMainScreen(navController: NavController = rememberNavController()) {

    val navButtons = mutableListOf<AMENavButton>()
    navButtons.add(AMENavButton(iconId = R.drawable.qr_code_scanner, buttonText = "Scan N' Match",
        onClick = { navController.navigate(AMEPlusScreens.WarehouseScanAndMatchScreen.name)}))
    navButtons.add(AMENavButton(iconId = R.drawable.local_shipping, buttonText = "Despatch",
        onClick = { navController.navigate(AMEPlusScreens.DespatchScreen.name )}))
    navButtons.add(AMENavButton(iconId = R.drawable.receipt_long, buttonText = "Bosch Label",
        onClick = { navController.navigate(AMEPlusScreens.DespatchScreen.name )}))
    navButtons.add(AMENavButton(iconId = R.drawable.barcode, buttonText = "Caterpillar",
        onClick = { navController.navigate(AMEPlusScreens.DespatchScreen.name )}))
    navButtons.add(AMENavButton(iconId = R.drawable.inventory, buttonText = "Stocktake",
        onClick = { navController.navigate(AMEPlusScreens.DespatchScreen.name )}))
    navButtons.add(AMENavButton(iconId = R.drawable.inventory_2, buttonText = "Inventory",
        onClick = { navController.navigate(AMEPlusScreens.DespatchScreen.name )}))
    navButtons.add(AMENavButton(iconId = R.drawable.content_cut, buttonText = "Cutting End",
        onClick = { navController.navigate(AMEPlusScreens.DespatchScreen.name )}))
    navButtons.add(AMENavButton(iconId = R.drawable.flight_land, buttonText = "P.O. ETA",
        onClick = { navController.navigate(AMEPlusScreens.DespatchScreen.name )}))
    navButtons.add(AMENavButton(iconId = R.drawable.settings, buttonText = "Setting",
        onClick = { navController.navigate(AMEPlusScreens.DespatchScreen.name )}))
    navButtons.add(AMENavButton(iconId = R.drawable.info, buttonText = "About",
        onClick = { navController.navigate(AMEPlusScreens.DespatchScreen.name )}))
Surface(modifier = Modifier.fillMaxSize().padding(4.dp)) {
    LazyVerticalGrid(columns = GridCells.Adaptive(minSize = 110.dp)) {
        items(navButtons.size) {index ->
            AMEIconButton(iconId = navButtons[index].iconId,
                buttonText = navButtons[index].buttonText,
                onClick = navButtons[index].onClick)
        }
    }
}

}

@Preview
@Composable
fun AMEIconButton(iconId: Int = R.drawable.qr_code_scanner,
                  buttonText: String = "Scan N' Match",
                  onClick: () -> Unit = {}) {
    Column(modifier = Modifier
        .size(110.dp)
        .padding(10.dp)
        .clickable { onClick() },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center) {
        Image(painter = painterResource(id = iconId),
            contentDescription = "", modifier = Modifier.size(50.dp))
        Text(text = buttonText, modifier = Modifier.padding(5.dp), style = MaterialTheme.typography.labelSmall)
    }
}

data class AMENavButton(val iconId: Int, val buttonText: String, val onClick: () -> Unit)