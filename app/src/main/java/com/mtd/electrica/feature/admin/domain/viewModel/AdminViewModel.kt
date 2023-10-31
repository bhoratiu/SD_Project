package com.mtd.electrica.feature.admin.domain.viewModel

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mtd.electrica.feature.admin.data.repository.AdminRepository
import com.mtd.electrica.feature.signin.data.model.User
import com.mtd.electrica.feature.signin.domain.useCase.DeleteUserUseCase
import com.mtd.electrica.feature.signin.domain.useCase.GetAllUsersUseCase
import com.mtd.electrica.feature.signin.domain.useCase.RegisterUseCase
import com.mtd.electrica.feature.signin.domain.viewModel.UserViewModel
import com.mtd.electrica.feature.user.data.repository.Device
import com.mtd.electrica.feature.user.data.repository.DeviceRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@SuppressLint("CheckResult")
@HiltViewModel
class AdminViewModel @Inject constructor(
    private val deviceRepository: AdminRepository,
    private val registerUseCase: RegisterUseCase,
    private val deleteUserUseCase: DeleteUserUseCase,
    private val getAllUsersUseCase: GetAllUsersUseCase
) : ViewModel() {

    private val _devices = MutableStateFlow<List<Device>>(emptyList())
    val devices: StateFlow<List<Device>> = _devices

    private val _successMessage = MutableStateFlow("")
    val successMessage: StateFlow<String> = _successMessage.asStateFlow()

    private val _errorMessage = MutableStateFlow("")
    val errorMessage: StateFlow<String> = _errorMessage.asStateFlow()

    init {
        loadDevices()
    }

    fun loadDevices() {
        val devices = deviceRepository.getAllDevices()
        devices.subscribe(
            {
                _devices.value = it
            },
            {
                _errorMessage.value = "Failed to load devices"
            }
        )
    }

    fun addDevice(
        id: Long,
        description: String?,
        maximumHourlyEnergyConsumption: String?,
        userIds: String
    ) {
        val userIdsList = if (userIds.isNullOrBlank()) {
            null
        } else {
            userIds.split(",").mapNotNull { it.trim().toLongOrNull() }
        }
        val maximumHourlyEnergyConsumptionAsLong =
            if (maximumHourlyEnergyConsumption.isNullOrBlank()) {
                null
            } else {
                maximumHourlyEnergyConsumption.toLong()
            }
        deviceRepository.addDevice(
            Device(
                id = id,
                description = description,
                maximumHourlyEnergyConsumption = maximumHourlyEnergyConsumptionAsLong,
                userIds = userIdsList
            )
        ).subscribe({
            loadDevices()
            _successMessage.value = "Device added successfully."
        }, {
            _errorMessage.value = "Failed to add device"
        })
    }

    fun updateDevice(
        id: Long,
        description: String?,
        maximumHourlyEnergyConsumption: String?,
        userIds: String?
    ) {
        val userIdsList = if (userIds.isNullOrBlank()) {
            null
        } else {
            userIds.split(",").mapNotNull { it.trim().toLongOrNull() }
        }
        val maximumHourlyEnergyConsumptionAsLong =
            if (maximumHourlyEnergyConsumption.isNullOrBlank()) {
                null
            } else {
                maximumHourlyEnergyConsumption.toLong()
            }

        deviceRepository.updateDevice(
            id, Device(
                id = 0,
                description = description,
                maximumHourlyEnergyConsumption = maximumHourlyEnergyConsumptionAsLong,
                userIds = userIdsList
            )
        ).subscribe({
            loadDevices()
            _successMessage.value = "Device updated successfully"
        }, {
            _errorMessage.value = "Failed to update device"
        })
    }

    fun deleteDevice(id: Long) {
        viewModelScope.launch {
            deviceRepository.deleteDevice(id).subscribe({
                loadDevices()
                _successMessage.value = "Device deleted successfully"
            }, {
                _errorMessage.value = "Failed to delete device"
            })
        }
    }

    fun manageUserAssociations(deviceId: Long) {
        // Implement the logic to manage user-device associations
    }

    fun onSuccessMessageShown() {
        _successMessage.value = ""
    }

    fun onErrorMessageShown() {
        _errorMessage.value = ""
    }

    private val _users = MutableLiveData<List<User>>()
    val users: LiveData<List<User>> = _users

    fun loadAllUsers() {
        getAllUsersUseCase.getAllUsers()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ userList ->
                _users.value = userList
            }, { error ->
                // Handle error
            })
    }

    fun createUser(username: String, password: String) {
        registerUseCase.execute(username, password)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                _successMessage.value = "User updated successfully"
                loadAllUsers()
            }, {
                _errorMessage.value = "An error occurred"
            })
    }

    fun deleteUser(email: String, id: Long) {
        deleteUserUseCase.deleteUser(email, id)
            .subscribeOn(Schedulers.io())
            .subscribe({
                _successMessage.value = "User deleted successfully"
                loadAllUsers()
            }, { error ->
                _errorMessage.value = "An error occurred"
            })

    }
}