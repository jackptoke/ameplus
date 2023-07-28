package dev.toke.ameplus.screens.warehouse

import android.media.MediaPlayer
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import dev.toke.ameplus.R
import dev.toke.ameplus.data.ScanResult
import dev.toke.ameplus.data.ScanUiEvent
import dev.toke.ameplus.utils.ColorConstants
import dev.toke.ameplus.utils.Constants
import dev.toke.ameplus.utils.SystemBroadcastReceiver

@Composable
fun WarehouseScanAndMatchScreen(/*navController: NavController,*/
                                warehouseSAMSViewModel: WarehouseScanNMatchViewModel = hiltViewModel()) {

    val state = warehouseSAMSViewModel.scanState
    val context = LocalContext.current
    val numOfParts = warehouseSAMSViewModel.partsDataList.value?.count() ?: 0
    var isMismatched by remember {
        mutableStateOf(false)
    }
//        numOfParts == 2 && ((warehouseSAMSViewModel.partsDataList.value?.get(0)?.get(0)?.partNumber
//            ?: "y") != (warehouseSAMSViewModel.partsDataList.value?.get(1)
//            ?.get(0)?.partNumber ?: "x"))
    var isMatched: Boolean by remember {
        mutableStateOf(false)
    }
    var selectedPartIndex by remember {
        mutableStateOf(0)
    }

    LaunchedEffect(warehouseSAMSViewModel, context) {
        warehouseSAMSViewModel.scanResults.collect {result ->
            when(result) {
                is ScanResult.Success -> {
                    Toast.makeText(context,"Success", Toast.LENGTH_SHORT).show()
                    isMismatched =
                        warehouseSAMSViewModel.partsDataList.value?.count() == 2 && ((warehouseSAMSViewModel.partsDataList.value?.get(0)?.get(0)?.partNumber
                            ?: "y") != (warehouseSAMSViewModel.partsDataList.value?.get(1)
                            ?.get(0)?.partNumber ?: "x"))
                    isMatched = warehouseSAMSViewModel.partsDataList.value?.count() == 2 && (warehouseSAMSViewModel.partsDataList.value?.get(0)?.get(0)?.partNumber
                        == warehouseSAMSViewModel.partsDataList.value?.get(1)?.get(0)?.partNumber)
                    if(isMismatched) warehouseSAMSViewModel.playErrorSound()
                    else if(isMatched) warehouseSAMSViewModel.playGoodSound()
                }
                is ScanResult.NotFound -> {
                    Toast.makeText(context,"Not found", Toast.LENGTH_SHORT).show()
                }
                is ScanResult.Error -> {
                    Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    // Waiting to receive scanning event from the scanner
    SystemBroadcastReceiver(systemAction = Constants.ACTIVITY_INTENT_FILTER_ACTION) {receivedIntent ->
        val action = receivedIntent?.action ?: return@SystemBroadcastReceiver
        Log.d("BroadCastReceiver", action)
        if(action == Constants.ACTIVITY_INTENT_FILTER_ACTION) {
            val barcode = receivedIntent.getStringExtra(Constants.DATAWEDGE_INTENT_KEY_DATA) ?: ""
            if(barcode.isNotBlank()) {
                if(state.firstBarcode.isBlank())
                    warehouseSAMSViewModel.onEvent(ScanUiEvent.FirstScanEvent(barcode = barcode))
                else if(state.secondBarcode.isBlank())
                    warehouseSAMSViewModel.onEvent(ScanUiEvent.SecondScanEvent(barcode = barcode))
                else if(state.firstBarcode.isNotBlank() && state.secondBarcode.isNotBlank())
                {
                    warehouseSAMSViewModel.onEvent(ScanUiEvent.ScanResetEvent(barcode = barcode))
                }
            }
        }
    }

    Column(verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()) {
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp, end = 15.dp)
            .height(40.dp),
            horizontalArrangement = Arrangement.End
        ){
            warehouseSAMSViewModel.partsDataList.value?.let {partsList ->
                for( part in partsList) {
                    Icon(imageVector = Icons.Filled.Favorite,
                        contentDescription = "Heart icon",
                        modifier = Modifier.width(30.dp),
                        tint = Color.Red
                        )
                }
                val emptyCount = 2 - partsList.count()
                for(i in 1..emptyCount){
                    Icon(imageVector = Icons.Filled.FavoriteBorder,
                        contentDescription = "Heart icon",
                        modifier = Modifier.width(30.dp),
                        tint = Color.Black
                    )
                }
            }

        }
        Surface(modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth()
            .height(440.dp),
            color = MaterialTheme.colorScheme.background
            ) {
            Column(verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally) {
                if(warehouseSAMSViewModel.partsDataList.value?.count()!! > 0) {
                    LazyRow {
                        warehouseSAMSViewModel.partsDataList.value?.let {
                            items(it[selectedPartIndex]) {part ->
                                PartShowcase(title = part.partNumber, subTitle = part.partTitle,
                                    imageUrl = if(!part.imagePath.isNullOrEmpty()) "${Constants.IMAGE_BASE_URL}${part.imagePath}" else "")
                            }
                        }
                    }

                }
                if(isMismatched) {
                    Text(text = "PARTS MISMATCHED!",
                        style = MaterialTheme.typography.headlineLarge,
                        fontWeight = FontWeight.Black,
                        color = Color.Red.copy(alpha = 0.8f),
                        modifier = Modifier.padding(top = 25.dp))
                }
            }

        }

        Surface(modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth()
            .height(100.dp),
            color = MaterialTheme.colorScheme.background
        ) {
            Row(modifier = Modifier
                .fillMaxWidth()
                .height(80.dp),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically) {
                warehouseSAMSViewModel.partsDataList.value?.let { partsList ->
                    Row(horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .width(230.dp)
                            .height(80.dp)) {
                    if(partsList.count() == 0) {
                            Text(text = "READY", style = MaterialTheme.typography.headlineLarge,
                                color = MaterialTheme.colorScheme.inversePrimary)
                    }
                    else {
                        for ((index, part) in partsList.withIndex()) {
                            AsyncImage(
                                model = if(part[0].imagePath.isNullOrEmpty()) R.drawable.no_image_placeholder else "${Constants.IMAGE_BASE_URL}${part[0].imagePath}",
                                contentDescription = "Part Description",
                                contentScale = ContentScale.FillHeight,
                                modifier = Modifier
                                    .height(80.dp)
                                    .width(80.dp)
                                    .clip(RoundedCornerShape(5.dp))
                                    .clickable { selectedPartIndex = index }
                            )
                        }
                        val emptyCount = 2 - partsList.count()
                        for (i in 1..emptyCount) {
                            Surface(
                                modifier = Modifier
                                    .size(80.dp),
                                color = MaterialTheme.colorScheme.background,
                                border = BorderStroke(width = 5.dp, Color(0xff9dc284)),
                                shape = RoundedCornerShape(5.dp)
                            ) {

                            }
                        }
                    }

                    }

                }
                Surface(modifier = Modifier
                    .size(80.dp),
                    color = if(isMismatched) ColorConstants.BadColor else ColorConstants.GoodColor,
                    shape = RoundedCornerShape(5.dp)
                ){
                    Column(verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("$numOfParts", style = MaterialTheme.typography.headlineLarge)
                    }
                }
            }
            
        }
    }


}

@Preview
@Composable
fun PartShowcase(imageUrl: String = "https://images.unsplash.com/photo-1608499337372-2fea1e07da37",
                 title: String = "D92-1184-001", subTitle: String = "Revision: A") {
    Column(modifier = Modifier
        .padding(6.dp)
        .width(335.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center) {
        AsyncImage(model = imageUrl,
            contentDescription = "Part Description",
            contentScale = ContentScale.FillWidth,
            modifier = Modifier.height(200.dp)
            )
        Text(text = title, style = MaterialTheme.typography.displayMedium)
        Text(text = subTitle, style = MaterialTheme.typography.labelSmall)
    }
}

data class Part(val partNumber: String = "", val revision: String = "", val imageUrl: String = "")

