package dev.toke.ameplus.screens.warehouse

import android.content.Intent
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavController
import coil.compose.AsyncImage
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
        PartShowcase()
        PartShowcase()
    }
}

@Preview
@Composable
fun PartShowcase(imageUrl: String = "https://images.unsplash.com/photo-1608499337372-2fea1e07da37",
                 title: String = "D92-1184-001", subTitle: String = "Revision: A") {
    Column(modifier = Modifier
        .padding(6.dp)
        .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center) {
        AsyncImage(model = "https://images.unsplash.com/photo-1608499337372-2fea1e07da37",
            contentDescription = "Part Description",
            modifier = Modifier.fillMaxWidth())
        Text(text = "D92-1184-001", style = MaterialTheme.typography.displayMedium)
        Text(text = "REVISION: A", style = MaterialTheme.typography.labelSmall)
    }
}

data class Part(val partNumber: String, val revision: String, val imageUrl: String)

