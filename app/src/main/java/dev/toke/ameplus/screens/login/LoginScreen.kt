package dev.toke.ameplus.screens.login

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import dev.toke.ameplus.R
import dev.toke.ameplus.data.AuthResult
import dev.toke.ameplus.navigation.AMEPlusScreens

@Composable
fun LoginScreen(navController: NavController,
                viewModel: LoginViewModel = hiltViewModel()) {
//    var username by remember {
//        mutableStateOf("")
//    }

    val state = viewModel.state
    val context = LocalContext.current

    LaunchedEffect(viewModel, context) {
        viewModel.authResult.collect { result ->
            when(result) {
                is AuthResult.Authorized -> {
                    navController.navigate(AMEPlusScreens.AMEPlusMainScreen.name)
                }
                is AuthResult.Unauthorized -> {
                    Toast.makeText(context,
                        "You are not authorized",
                        Toast.LENGTH_LONG).show()
                }
                is AuthResult.UnknownError -> {
                    Toast.makeText(context,
                        "An unknown error occurred",
                        Toast.LENGTH_LONG).show()
                }
            }
        }
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
                OutlinedTextField(value = state.signInUsername, onValueChange =  {
                    viewModel.onEvent(AuthUiEvent.SignInUsernameChanged(it))},
                    label = { Text("AME Username")})
                Button(modifier = Modifier.padding(10.dp), onClick = {
                    viewModel.onEvent(AuthUiEvent.SignIn)
                }) {
                    Text("Login")
                }
            }
        }
    }
}