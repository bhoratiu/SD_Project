package com.mtd.electrica.feature.user.domain.viewModel

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mtd.electrica.common.data.dataSource.local.LocalStorage
import com.mtd.electrica.feature.user.data.model.EnergyConsumptionData
import com.mtd.electrica.feature.user.data.repository.Device
import com.mtd.electrica.feature.user.data.repository.DeviceRepository
import com.mtd.electrica.feature.webSocket.EnergyWebSocketClient
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import javax.inject.Inject

@HiltViewModel
class DeviceViewModel @Inject constructor(
    private val deviceRepository: DeviceRepository,
    private val localStorage: LocalStorage
) : ViewModel() {
    private val _devices = MutableLiveData<List<Device>>()
    val devices: LiveData<List<Device>> = _devices

    @SuppressLint("CheckResult")
    fun loadUserDevices() {
        val userId = localStorage.getUserId()
        if (userId != null) {
            deviceRepository.getUserDevices(userId.toLong())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ deviceList ->
                    _devices.value = deviceList
                }, { throwable ->
                    Log.e("DeviceViewModel", "Error loading devices", throwable)
                })
        }
    }

    private val _webSocketMessage = MutableLiveData<String>()
    val webSocketMessage: LiveData<String> = _webSocketMessage

    private var webSocketClient: EnergyWebSocketClient? = null

    fun connectWebSocket() {
        webSocketClient = EnergyWebSocketClient("ws://10.0.2.2:8087/ws") { message ->
            _webSocketMessage.postValue(message);
            Log.d("webSocket", message);
        }
    }

    private val _associationStatus = MutableLiveData<String>()
    val associationStatus: LiveData<String> = _associationStatus

    @SuppressLint("CheckResult")
    fun associateDevice(deviceId: Long) {
        val userId = localStorage.getUserId()
        if (userId != null) {
            deviceRepository.associateDeviceWithUser(deviceId, userId.toLong())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ response ->
                    if (response.isSuccessful) {
                        _associationStatus.value = "Device associated successfully"
                    } else {
                        _associationStatus.value = "Failed to associate device"
                    }
                }, { throwable ->
                    _associationStatus.value = "Error: ${throwable.message}"
                })
        }
    }

    private val _energyData = MutableLiveData<List<EnergyConsumptionData>>()
    val energyData: LiveData<List<EnergyConsumptionData>> = _energyData

    fun loadEnergyData(date: String) {
        devices.value?.first()?.let {
            deviceRepository.getEnergyConsumptionData(it.id, date)
                .subscribe({ data ->
                    _energyData.postValue(data)
                }, { error ->
                    Log.e("EnergyViewModel", "Error fetching energy data", error)
                })
        }
    }
}