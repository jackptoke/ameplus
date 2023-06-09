package dev.toke.ameplus.screens.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import dev.toke.ameplus.R
import dev.toke.ameplus.navigation.AMEPlusScreens

@Composable
fun LoginScreen(navController: NavController) {
    var username by remember {
        mutableStateOf("")
    }
    Surface(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.padding(35.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center) {
            Image(painter = painterResource(id = R.drawable.ameplus),
                contentDescription = "AME Plus logo",
                contentScale = ContentScale.Fit,
                modifier = Modifier.width(155.dp))
            Column(modifier = Modifier.padding(top = 135.dp),
                horizontalAlignment = Alignment.CenterHorizontally) {
                OutlinedTextField(value = username, onValueChange =  {
                    username = it },
                    label = { Text("AME Username")})
                Button(modifier = Modifier.padding(10.dp), onClick = {
                    navController.navigate(AMEPlusScreens.AMEPlusMainScreen.name)
                }) {
                    Text("Login")
                }
            }
        }
    }
}