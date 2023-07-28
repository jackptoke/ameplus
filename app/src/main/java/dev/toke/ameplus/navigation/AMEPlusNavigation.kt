package dev.toke.ameplus.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dev.toke.ameplus.screens.despatch.DespatchScreen
import dev.toke.ameplus.screens.login.LoginScreen
import dev.toke.ameplus.screens.main.AMEPlusMainScreen
import dev.toke.ameplus.screens.splash.AMEPlusSplashScreen
import dev.toke.ameplus.screens.sorting.SortingScreen
import dev.toke.ameplus.screens.warehouse.WarehouseScanAndMatchScreen

@Composable
fun AMEPlusNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = AMEPlusScreens.AMEPlusSplashScreen.name) {
        composable(AMEPlusScreens.AMEPlusSplashScreen.name) {
            AMEPlusSplashScreen(navController = navController)
        }

        composable(AMEPlusScreens.AMEPlusMainScreen.name) {
            AMEPlusMainScreen(navController = navController)
        }
        
        composable(AMEPlusScreens.DespatchScreen.name) {
            DespatchScreen(/*navController = navController*/)
        }

        composable(AMEPlusScreens.WarehouseScanAndMatchScreen.name) {
            WarehouseScanAndMatchScreen(/*navController = navController*/)
        }

        composable(AMEPlusScreens.SortingScreen.name) {
            SortingScreen(/*navController = navController*/)
        }
        
        composable(AMEPlusScreens.LoginScreen.name) {
            LoginScreen(navController = navController)
        }


    }
}