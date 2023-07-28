package dev.toke.ameplus.screens.sorting

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.VerticalAlignmentLine
import androidx.compose.ui.layout.layout
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import dev.toke.ameplus.components.BarcodeText

@Composable
fun SortingScreen(/*navController: NavController*/) {

    val planId by remember {
        mutableStateOf(TextFieldValue(""))
    }
    val batchNumber by remember {
        mutableStateOf(TextFieldValue(""))
    }
    val harness by remember {
        mutableStateOf(TextFieldValue(""))
    }
    val circuit by remember {
        mutableStateOf(TextFieldValue("28"))
    }
    val scannedTub by remember {
        mutableStateOf(TextFieldValue("23A/1"))
    }
    val selectedTub by remember {
        mutableStateOf(TextFieldValue("42A/1"))
    }

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top) {
        Column {
            BarcodeText(
                text = planId,
                label = "Plan Id",
                modifier = Modifier
                    .fillMaxWidth(), enabled = false,
                style = MaterialTheme.typography.headlineLarge)
            BarcodeText(
                text = batchNumber,
                label = "Batch Number",
                modifier = Modifier
                    .fillMaxWidth(), enabled = false,
                style = MaterialTheme.typography.headlineLarge)
            BarcodeText(
                text = harness,
                label = "Harness",
                modifier = Modifier
                    .fillMaxWidth(), enabled = false,
                style = MaterialTheme.typography.headlineLarge)
        }
        Spacer(modifier = Modifier.height(50.dp))
        Column {
            Row {
                Column(modifier = Modifier.padding(5.dp)) {
                    BarcodeText(
                        text = circuit,
                        label = "Circuit",
                        modifier = Modifier
                            .width(width = 150.dp), enabled = false,
                        style = MaterialTheme.typography.headlineLarge)
                    BarcodeText(
                        text = scannedTub,
                        label = "Scanned Tub",
                        modifier = Modifier
                            .width(width = 150.dp), enabled = false,
                        style = MaterialTheme.typography.headlineLarge)
                }
                Box(
                    modifier = Modifier.width(200.dp).height(178.dp).padding(5.dp),
                    contentAlignment = androidx.compose.ui.Alignment.CenterStart
                ) {
                    OutlinedTextField(
                        value = selectedTub.text,
                        label = { Text("Selected Tub",
                            style = MaterialTheme.typography.titleSmall) },
                        onValueChange = {},
                        textStyle = MaterialTheme.typography
                            .displayMedium
                            .copy(color = Color.Red,
                                fontWeight = FontWeight.Bold),
                        singleLine = true,
                        visualTransformation = VisualTransformation.None,
                        modifier = Modifier
                            .fillMaxHeight(),
                        enabled = false,


                        )
                }

            }
            Row(
                modifier = Modifier.fillMaxWidth()
                    .padding(10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center) {
                Button(onClick = { /*TODO*/ }) {
                    Icon(Icons.Filled.Refresh, contentDescription = "Restart Button")
                    Text(text = "Restart")
                }
            }

        }

    }
}

@Preview
@Composable
fun PreviewSortingScreen() {
    SortingScreen()
}

