package dev.toke.ameplus.screens.despatch

import android.media.MediaPlayer
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import dev.toke.ameplus.R
import dev.toke.ameplus.components.BarcodeText
import dev.toke.ameplus.data.ScanUiEvent
import dev.toke.ameplus.utils.ColorConstants
import dev.toke.ameplus.utils.Constants
import dev.toke.ameplus.utils.SystemBroadcastReceiver
import kotlinx.coroutines.flow.collect

@Composable
fun DespatchScreen(/*navController: NavController,*/
                   despatchViewModel: DespatchViewModel = hiltViewModel()) {
    var partNumber by remember {
        mutableStateOf(TextFieldValue("Part Number"))
    }
    var barcodeTextPartNumber by remember {
        mutableStateOf(TextFieldValue("Harness Label"))
    }
    var feedbackImage by remember {
        mutableStateOf(Icons.Filled.Face)
    }
    var feedbackColor by remember {
        mutableStateOf(ColorConstants.GoodColor)
    }

    var isNotMatched by remember {
        mutableStateOf(false)
    }
    var isMatched by remember {
        mutableStateOf(false)
    }

    val state = despatchViewModel.scanState
    val context = LocalContext.current

    LaunchedEffect(despatchViewModel, context) {
        despatchViewModel.scanResults.collect {result ->
            var color = ColorConstants.GoodColor
            var iconImage = Icons.Filled.CheckCircle
            isNotMatched = false
            isMatched = false
            when(result) {
                is DespatchScanResult.PartNumberSuccess -> {
                    Log.d("DespatchViewModel", "Scan Result - PartNumberSuccess - ${ state.partFromProductNumber }")
                    partNumber = TextFieldValue(despatchViewModel.scanState.partFromProductNumber?.pt_Part ?: "Part Number")
                }

                is DespatchScanResult.BarcodeTextSuccess -> {
                    barcodeTextPartNumber = TextFieldValue(despatchViewModel.scanState.partFromBarcodeText?.partNumber ?: "Harness Label")
                }

                is DespatchScanResult.NotFound -> {
                    // TODO - Not found
                    color = ColorConstants.BadColor
                    iconImage = Icons.Rounded.FavoriteBorder
                    Toast.makeText(context, "Irrelevant barcode", Toast.LENGTH_SHORT)
                }

                is DespatchScanResult.Error -> {
                    color = ColorConstants.BadColor
                    iconImage = Icons.Filled.Clear
                    Toast.makeText(context, "An error occurred", Toast.LENGTH_SHORT)
                }
            }
            if(despatchViewModel.scanState.partFromProductNumber != null && despatchViewModel.scanState.partFromBarcodeText != null
                && partNumber.text != barcodeTextPartNumber.text) {
                isNotMatched = true
                color = ColorConstants.BadColor
                iconImage = Icons.Filled.Close
            }
            else if(despatchViewModel.scanState.partFromProductNumber != null && despatchViewModel.scanState.partFromBarcodeText != null
                && partNumber.text == barcodeTextPartNumber.text) {
                isMatched = true;
            }
            feedbackImage = iconImage
            feedbackColor = color

        }
    }

    LaunchedEffect(key1 = isNotMatched, key2 = isMatched, context) {
        if(isNotMatched) {
            despatchViewModel.playErrorSound()
        }
        else if(isMatched) {
            despatchViewModel.playGoodSound()

        }
    }


    // Waiting to receive scanning event from the scanner
    SystemBroadcastReceiver(systemAction = Constants.ACTIVITY_INTENT_FILTER_ACTION) { receivedIntent ->
        val action = receivedIntent?.action ?: return@SystemBroadcastReceiver
        Log.d("BroadCastReceiver", action)
        if(action == Constants.ACTIVITY_INTENT_FILTER_ACTION) {
            val barcode = receivedIntent.getStringExtra(Constants.DATAWEDGE_INTENT_KEY_DATA) ?: ""
            if(barcode.isNotBlank()) {
                if(state.partFromProductNumber != null && state.partFromBarcodeText != null)
                {
                    partNumber = TextFieldValue("Part Number")
                    barcodeTextPartNumber = TextFieldValue("Harness Label")
                }
                despatchViewModel.onEvent(DespatchUiEvent.ScanEvent(barcode))
            }
        }
    }

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
        ) {
        BarcodeText(
            text = partNumber,
            label = "Part Number",
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth(),
            enabled = false,
            style = MaterialTheme.typography.headlineLarge
        )
        BarcodeText(
            text = barcodeTextPartNumber,
            label = "Harness Label",
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth(),
            enabled = false,
            style = MaterialTheme.typography.headlineLarge
        )
        Surface(modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth()
            .fillMaxHeight(),
            color = MaterialTheme.colorScheme.background) {
            Column(verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth()) {
                Icon(imageVector = feedbackImage,
                    contentDescription = "Checked/Crossed",
                    modifier = Modifier.size(100.dp),
                    tint = feedbackColor)
            }
        }
    }
}

