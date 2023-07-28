package dev.toke.ameplus.screens.main

import android.widget.Toast
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
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import dev.toke.ameplus.R
import dev.toke.ameplus.data.AuthResult
import dev.toke.ameplus.navigation.AMEPlusScreens
import dev.toke.ameplus.utils.ColorConstants

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun AMEPlusMainScreen(navController: NavController = rememberNavController(),
                      viewModel: MainViewModel = hiltViewModel()
) {

    val context = LocalContext.current

    LaunchedEffect(viewModel, context) {
        viewModel.authResult.collect { result ->
            when(result) {
                is AuthResult.Authorized -> {
                    //
                }
                is AuthResult.Unauthorized -> {
                    navController.navigate(AMEPlusScreens.LoginScreen.name) {
                        popUpTo(navController.graph.id) {
                            inclusive = true
                        }
                    }
                }
                is AuthResult.UnknownError -> {
                    Toast.makeText(context,
                        "An unknown error occurred",
                        Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    val navButtons = mutableListOf<AMENavButton>()
    navButtons.add(AMENavButton(iconId = R.drawable.qr_code_scanner, buttonText = "Scan N' Match",
        color = ColorConstants.PrimaryColor,
        onClick = { navController.navigate(AMEPlusScreens.WarehouseScanAndMatchScreen.name)}))
    navButtons.add(AMENavButton(iconId = R.drawable.local_shipping, buttonText = "Despatch",
        color = ColorConstants.PrimaryColor,
        onClick = { navController.navigate(AMEPlusScreens.DespatchScreen.name )}))
    navButtons.add(AMENavButton(iconId = R.drawable.content_cut, buttonText = "Sorting",
        color = ColorConstants.PrimaryColor,
        onClick = { navController.navigate(AMEPlusScreens.SortingScreen.name )}))
    navButtons.add(AMENavButton(iconId = R.drawable.receipt_long, buttonText = "Bosch Label",
        color = ColorConstants.PrimaryColor,
        onClick = { navController.navigate(AMEPlusScreens.DespatchScreen.name )}))
    navButtons.add(AMENavButton(iconId = R.drawable.barcode, buttonText = "Caterpillar",
        color = ColorConstants.PrimaryColor,
        onClick = { navController.navigate(AMEPlusScreens.DespatchScreen.name )}))
    navButtons.add(AMENavButton(iconId = R.drawable.inventory, buttonText = "Stocktake",
        color = ColorConstants.PrimaryColor,
        onClick = { navController.navigate(AMEPlusScreens.DespatchScreen.name )}))
    navButtons.add(AMENavButton(iconId = R.drawable.inventory_2, buttonText = "Inventory",
        color = ColorConstants.PrimaryColor,
        onClick = { navController.navigate(AMEPlusScreens.DespatchScreen.name )}))
    navButtons.add(AMENavButton(iconId = R.drawable.flight_land, buttonText = "P.O. ETA",
        color = ColorConstants.PrimaryColor,
        onClick = { navController.navigate(AMEPlusScreens.DespatchScreen.name )}))
    navButtons.add(AMENavButton(iconId = R.drawable.settings, buttonText = "Setting",
        color = ColorConstants.PrimaryColor,
        onClick = { navController.navigate(AMEPlusScreens.DespatchScreen.name )}))
    navButtons.add(AMENavButton(iconId = R.drawable.info, buttonText = "About",
        color = ColorConstants.PrimaryColor,
        onClick = { navController.navigate(AMEPlusScreens.DespatchScreen.name )}))
Scaffold(
    topBar = {
        TopAppBar(title = {
            Text("AME+")
        },
            actions = {
                      IconButton(onClick = { viewModel.onEvent(MainUiEvent.SignOutClicked) }) {
                          Icon(imageVector = Icons.Filled.ExitToApp, contentDescription = "Exit icon")
                      }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = ColorConstants.PrimaryColor,
                scrolledContainerColor = ColorConstants.SecondaryColor,
                actionIconContentColor = ColorConstants.TertiaryColor,
                navigationIconContentColor = ColorConstants.SecondaryColor,
                titleContentColor = ColorConstants.NeutralColor
            )
        )
    }
) { contentPadding ->
    Surface(modifier = Modifier
        .fillMaxSize()
        .padding(contentPadding)) {
        LazyVerticalGrid(columns = GridCells.Adaptive(minSize = 110.dp)) {
            items(navButtons.size) {index ->
                AMEIconButton(iconId = navButtons[index].iconId,
                    buttonText = navButtons[index].buttonText,
                    color = navButtons[index].color,
                    onClick = navButtons[index].onClick)
            }
        }
    }
}


}

@Preview
@Composable
fun AMEIconButton(iconId: Int = R.drawable.qr_code_scanner,
                  buttonText: String = "Scan N' Match",
                  color: Color = ColorConstants.PrimaryColor,
                  onClick: () -> Unit = {}) {
    Column(modifier = Modifier
        .size(110.dp)
        .padding(10.dp)
        .clickable { onClick() },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center) {
        Image(painter = painterResource(id = iconId),
            contentDescription = "", modifier = Modifier.size(50.dp),
            colorFilter = ColorFilter.tint(color))
        Text(text = buttonText, modifier = Modifier.padding(5.dp), style = MaterialTheme.typography.labelSmall)
    }
}

data class AMENavButton(val iconId: Int, val buttonText: String, val onClick: () -> Unit, val color: Color)