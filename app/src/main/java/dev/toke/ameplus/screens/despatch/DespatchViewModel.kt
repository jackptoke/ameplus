package dev.toke.ameplus.screens.despatch

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.toke.ameplus.models.ProductInfo
import dev.toke.ameplus.repositories.PartsRepository
import dev.toke.ameplus.repositories.ProductRepository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DespatchViewModel @Inject constructor(private val productRepository: ProductRepository,
                                            private val partsRepository: PartsRepository): ViewModel() {
    var scanState by mutableStateOf(DespatchScanState())

    private val scanResultChannel = Channel<DespatchScanResult<ProductInfo>>()
    val scanResults = scanResultChannel.receiveAsFlow()

    fun onEvent(event: DespatchUiEvent) {
        when(event) {
            is DespatchUiEvent.ScanEvent -> {
                checkBarcode(barcode = event.barcode)
            }
        }
    }

    private fun checkBarcode(barcode: String) {
        viewModelScope.launch {
            if(scanState.partFromBarcodeText != null && scanState.partFromProductNumber != null) {
                scanState = scanState.copy(isLoading = true, partFromProductNumber = null, partFromBarcodeText = null)
            } else scanState = scanState.copy(isLoading = true)
            Log.d("DespatchViewModel", "CheckBarcode - Start")
            var scanResult: DespatchScanResult<ProductInfo> = DespatchScanResult.NotFound()
            try {
                var isFirstAttemptSuccessful = false;
                // if the barcode received is 7 characters long
                // try to look in the product code table
                if(barcode.length == 7) {
                    Log.d("DespatchViewModel", "CheckBarcode - check - product code")
                    val firstAttempt = productRepository.getPartInfo(barcode)
                    val data = firstAttempt.await().data
                    Log.d("DespatchViewModel", "CheckBarcode - data - ${data?.toString()}")
                    if(data != null) {
                        scanState = scanState.copy(partFromBarcodeText = data)
                        isFirstAttemptSuccessful = true
                        scanResult = DespatchScanResult.BarcodeTextSuccess(data)
                    }
                }

                if(!isFirstAttemptSuccessful){
                    Log.d("DespatchViewModel", "CheckBarcode - check - part")
                    val secondAttempt = partsRepository.getPart(barcode)
                    val data = secondAttempt.await().data
                    Log.d("DespatchViewModel", "CheckBarcode - data - ${data?.toString()}")
                    if(data != null) {
                        scanState = scanState.copy(partFromProductNumber = data)
                        Log.d("DespatchViewModel", "Success - ${scanState.toString()}")
                        scanResult = DespatchScanResult.PartNumberSuccess(ProductInfo(partNumber = data.pt_Part))
                    }
                }
            }catch(ex: Exception) {
                scanResult = DespatchScanResult.Error()
            }
            scanState = scanState.copy(isLoading = false)
            scanResultChannel.send(scanResult)
        }
    }
}