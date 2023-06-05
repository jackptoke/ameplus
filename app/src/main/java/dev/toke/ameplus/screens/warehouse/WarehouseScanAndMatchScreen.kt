package dev.toke.ameplus.screens.warehouse

import android.content.Intent
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavController
import dev.toke.ameplus.utils.Constants
import dev.toke.ameplus.utils.SystemBroadcastReceiver

@Composable
fun WarehouseScanAndMatchScreen(navController: NavController) {
    val scannerAction = remember {
        mutableStateOf("")
    }
    val barcodeData = remember {
        mutableStateOf("")
    }
    val scannerSource = remember {
        mutableStateOf("")
    }
    val scannerLabel = remember {
        mutableStateOf("")
    }

//    val scannerIntent = Intent(Constants.ACTIVITY_INTENT_FILTER_ACTION)
//    val startScannerIntentForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
//
//    }

    SystemBroadcastReceiver(systemAction = Constants.ACTIVITY_INTENT_FILTER_ACTION) {receivedIntent ->
        scannerAction.value = receivedIntent?.action ?: return@SystemBroadcastReceiver
        Log.d("BroadCastReceiver", scannerAction.value)
        if(scannerAction.value == Constants.ACTIVITY_INTENT_FILTER_ACTION) {
            scannerSource.value = receivedIntent?.getStringExtra(Constants.DATAWEDGE_INTENT_KEY_SOURCE) ?: ""
            barcodeData.value = receivedIntent?.getStringExtra(Constants.DATAWEDGE_INTENT_KEY_DATA) ?: ""
            scannerLabel.value = receivedIntent?.getStringExtra(Constants.DATAWEDGE_INTENT_LABEL_TYPE) ?: ""
        }
    }
    Column(modifier = Modifier.padding(8.dp)) {
        Text(text = "Action: ${scannerAction.value}", style = MaterialTheme.typography.headlineMedium)
        Text(text = "Source: ${scannerSource.value}", style = MaterialTheme.typography.titleSmall)
        Text(text = "Barcode: ${barcodeData.value}", style = MaterialTheme.typography.bodyMedium)
        Text(text = "Label: ${scannerLabel.value}", style = MaterialTheme.typography.labelSmall)
    }

}

