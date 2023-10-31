package com.mtd.electrica.feature.user.domain.viewModel

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mtd.electrica.common.data.dataSource.local.LocalStorage
import com.mtd.electrica.feature.user.data.repository.Device
import com.mtd.electrica.feature.user.data.repository.DeviceRepository
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
}