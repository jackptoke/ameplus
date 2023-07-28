package dev.toke.ameplus.screens.sorting

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
import dev.toke.ameplus.dtos.TubAndPlanDetail
import dev.toke.ameplus.models.PlanDetail
import dev.toke.ameplus.models.SortingTub
import dev.toke.ameplus.repositories.SortingRepository
import dev.toke.ameplus.services.ExoPlayerService
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SortingViewModel @Inject constructor(
    private val sortingRepository: SortingRepository,
    private val exoPlayerService: ExoPlayerService
) : ViewModel() {
    var sortingState by mutableStateOf(SortingState(null,  null, null, null, null))

    private val wireScanResultChannel = Channel<ScanResult<TubAndPlanDetail>> ()
    var scanResults = wireScanResultChannel.receiveAsFlow()

    private val _planDetail = MutableLiveData<PlanDetail?>(null)
    private val _sortingTub = MutableLiveData<SortingTub?>(null)
    private val _isLoading = MutableLiveData<Boolean>(false)
    val planDetail: LiveData<PlanDetail?>
        get() = _planDetail
    val sortingTub: LiveData<SortingTub?>
        get() = _sortingTub
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    // These get triggered by the barcode scans
    fun onEvent(event: ScanUiEvent) {
        when(event) {
            is ScanUiEvent.FirstScanEvent -> {
                sortingState = sortingState.copy(scanEvent = event)
                if(event.barcode.isNotBlank()) {
                    val planDetailId = event.barcode.toLongOrNull()
                    if(planDetailId !== null) fetchTubAndPlanDetailData(planDetailId)
                    else notifyError()
                }else {
                    notifyError()
                }
            }
            is ScanUiEvent.SecondScanEvent -> {}
            is ScanUiEvent.ScanResetEvent -> {}
            else -> {}
        }
    }

    private fun fetchTubAndPlanDetailData(planDetailId: Long) {
        viewModelScope.launch {
            _isLoading.value = true
            var scanResult: ScanResult<TubAndPlanDetail>

            try {
                val result = sortingRepository.getPlanAndTubDetail(planDetailId)
                val data = result.await().data
                Log.d("SortingViewModel", "data: $data")
                if(data !== null) {
                   _planDetail.value = data.planDetail
                    _sortingTub.value = data.sortingTub
                    sortingState = sortingState.copy(
                        planDetail = data.planDetail,
                        sortingTub = data.sortingTub,
                        scanEvent = ScanUiEvent.SecondScanEvent("")
                    )
                    scanResult = ScanResult.Success(data)
                }
                else {
                    scanResult = ScanResult.NotFound()
                }
            }
            catch(exception: Exception) {
                scanResult = ScanResult.Error()
            }
            _isLoading.value = false
            wireScanResultChannel.send(scanResult)
        }
    }

    fun playGoodSound() {
        exoPlayerService.playGoodSound()
    }

    fun playErrorSound() {
        exoPlayerService.playError()
    }

    private fun notifyError() {
        viewModelScope.launch {
            wireScanResultChannel.send(ScanResult.Error())
        }
    }

    private fun notifyNotFound() {
        viewModelScope.launch {
            wireScanResultChannel.send(ScanResult.NotFound())
        }
    }
}