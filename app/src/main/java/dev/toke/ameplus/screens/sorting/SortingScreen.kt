package dev.toke.ameplus.screens.sorting

import android.widget.Toast
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.VerticalAlignmentLine
import androidx.compose.ui.layout.layout
import androidx.compose.ui.platform.LocalContext
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
import androidx.hilt.navigation.compose.hiltViewModel
import dev.toke.ameplus.components.BarcodeText
import dev.toke.ameplus.data.ScanResult
import dev.toke.ameplus.data.ScanUiEvent
import dev.toke.ameplus.utils.Constants
import dev.toke.ameplus.utils.SystemBroadcastReceiver
import kotlinx.coroutines.flow.collectIndexed

@Composable
fun SortingScreen(/*navController: NavController*/ sortingViewModel: SortingViewModel = hiltViewModel()) {

    var planId by remember {
        mutableStateOf(TextFieldValue(""))
    }
    var batchNumber by remember {
        mutableStateOf(TextFieldValue(""))
    }
    var harness by remember {
        mutableStateOf(TextFieldValue(""))
    }
    var circuit by remember {
        mutableStateOf(TextFieldValue("28"))
    }
    var scannedTub by remember {
        mutableStateOf(TextFieldValue("23A/1"))
    }
    var selectedTub by remember {
        mutableStateOf(TextFieldValue("42A/1"))
    }

    // Setting up the initial state of the work process
    val state = sortingViewModel.sortingState
    val context = LocalContext.current

    val isValidPartDetail by remember {
        mutableStateOf(false )
    }
    val isValidTub by remember {
        mutableStateOf(false)
    }

    // Waiting on the result of the scan
    LaunchedEffect(sortingViewModel, context) {
        sortingViewModel.scanResults.collect {result ->
            when(result) {
                is ScanResult.Success -> {
                    sortingViewModel.playGoodSound()
                }
                is ScanResult.NotFound -> {
                    Toast.makeText(context, "This scan returns no result", Toast.LENGTH_SHORT).show()
                }
                is ScanResult.Error -> {
                    Toast.makeText(context, "Something went bad", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    SystemBroadcastReceiver(systemAction = Constants.ACTIVITY_INTENT_FILTER_ACTION) { receivedIntent ->
        val action = receivedIntent?.action ?: return@SystemBroadcastReceiver

        if(action === Constants.ACTIVITY_INTENT_FILTER_ACTION) {
            val barcode = receivedIntent.getStringExtra(Constants.DATAWEDGE_INTENT_KEY_DATA) ?: ""
            if(barcode.isNotBlank()) {
                if(sortingViewModel.sortingState.planDetail === null || sortingViewModel.sortingState.sortingTub === null) {
                    sortingViewModel.onEvent(ScanUiEvent.FirstScanEvent(barcode = barcode))
                }
                else if(sortingViewModel.sortingState.sortingTub !== null
                    && sortingViewModel.sortingState.sortingTub!!.tubId === barcode.toIntOrNull()) {
//                    sortingViewModel.onEvent(ScanUiEvent.ScanResetEvent(barcode))
                    Toast.makeText(context, "Looks like we have a match.", Toast.LENGTH_SHORT).show()
                }
                else {
                    Toast.makeText(context, "It seems like we have a problem", Toast.LENGTH_SHORT).show()
                }
            }

        }
    }

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top) {
        Column {
            BarcodeText(
                text = TextFieldValue("${sortingViewModel.planDetail.value?.planId}"),
                label = "Plan Id",
                modifier = Modifier
                    .fillMaxWidth(), enabled = false,
                style = MaterialTheme.typography.headlineLarge)
            BarcodeText(
                text = TextFieldValue("${sortingViewModel.planDetail.value?.batchId}"),
                label = "Batch Number",
                modifier = Modifier
                    .fillMaxWidth(), enabled = false,
                style = MaterialTheme.typography.headlineLarge)
            BarcodeText(
                text = TextFieldValue("${sortingViewModel.planDetail.value?.harness}"),
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
                        text = TextFieldValue("${sortingViewModel.planDetail.value?.circuit}"),
                        label = "Circuit",
                        modifier = Modifier
                            .width(width = 150.dp), enabled = false,
                        style = MaterialTheme.typography.headlineLarge)
                    BarcodeText(
                        text = TextFieldValue("${sortingViewModel.sortingTub.value?.tubNumber} ${sortingViewModel.sortingTub.value?.tubVariation} ${sortingViewModel.sortingTub.value?.tubSequence}/${sortingViewModel.sortingTub.value?.tubCount}"),
                        label = "Scanned Tub",
                        modifier = Modifier
                            .width(width = 150.dp), enabled = false,
                        style = MaterialTheme.typography.headlineLarge)
                }
                Box(
                    modifier = Modifier
                        .width(200.dp)
                        .height(178.dp)
                        .padding(5.dp),
                    contentAlignment = androidx.compose.ui.Alignment.CenterStart
                ) {
                    OutlinedTextField(
                        value = TextFieldValue("${sortingViewModel.sortingTub.value?.tubNumber} ${sortingViewModel.sortingTub.value?.tubVariation} ${sortingViewModel.sortingTub.value?.tubSequence}/${sortingViewModel.sortingTub.value?.tubCount}"),
                        label = { Text("Selected Tub",
                            style = MaterialTheme.typography.titleSmall) },
                        onValueChange = {},
                        textStyle = MaterialTheme.typography
                            .displaySmall
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
                modifier = Modifier
                    .fillMaxWidth()
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

