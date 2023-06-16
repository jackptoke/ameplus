package dev.toke.ameplus.screens.warehouse

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.toke.ameplus.data.ScanResult
import dev.toke.ameplus.data.ScanUiEvent
import dev.toke.ameplus.models.PartsData
import dev.toke.ameplus.repositories.PartsRepository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WarehouseScanNMatchViewModel @Inject constructor(val partsRepository: PartsRepository) : ViewModel() {
    var scanState by mutableStateOf(ScanAndMatchState())

    private val scanResultChannel = Channel<ScanResult<PartsData>>()
    val scanResults = scanResultChannel.receiveAsFlow()

    private val _partsDataList = MutableLiveData(mutableListOf<PartsData>())
    val partsDataList: LiveData<MutableList<PartsData>>
        get() = _partsDataList

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    fun onEvent(event: ScanUiEvent) {

            when(event) {
                is ScanUiEvent.FirstScanEvent -> {
                    scanState = scanState.copy( scanEvent = event)
                    fetchPartData(event.barcode)
                }
                is ScanUiEvent.SecondScanEvent -> {
                    scanState = scanState.copy( scanEvent = event)
                    fetchPartData(event.barcode)
                }
                is ScanUiEvent.ScanResetEvent -> {
                    scanState = scanState.copy(firstBarcode = "",
                        secondBarcode = "",
                        scanEvent = event)
                    _partsDataList.value?.clear()
                    fetchPartData(event.barcode)
                }
                else -> {}
            }
    }

    private fun fetchPartData(partNumber: String) {
        viewModelScope.launch {
            _isLoading.value = true
            var scanResult: ScanResult<PartsData> = ScanResult.NotFound()
            try{
                val result = partsRepository.getParts(partNumber)
                val data = result.await().data
                Log.d("WSNMViewModel", data.toString())
                if((data?.count() ?: 0) > 0) {
                    _partsDataList.value?.add(data!!)
                    Log.d("WSNMViewModel", "Count items: ${partsDataList.value?.count()}")

                    when(scanState.scanEvent) {
                        is ScanUiEvent.FirstScanEvent -> {
                            scanState = scanState.copy( firstBarcode = partNumber)
                        }
                        is ScanUiEvent.SecondScanEvent -> {
                            scanState = scanState.copy(secondBarcode = partNumber)
                        }
                        is ScanUiEvent.ScanResetEvent -> {
                            scanState = scanState.copy(firstBarcode = partNumber)
                        }
                    }
                    scanResult = ScanResult.Success(data = data)
                }
                else {
                    Log.d("WSNMViewModel", "failed")
                }
            }catch(exception: Exception) {

                scanResult = ScanResult.Error()
            }
            _isLoading.value = false
            scanResultChannel.send(scanResult)
        }

    }
}