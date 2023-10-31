package com.mtd.electrica.feature.admin.data.repository

import com.mtd.electrica.feature.admin.data.dataSource.remote.AdminAPI
import com.mtd.electrica.feature.user.data.repository.Device
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class AdminRepository @Inject constructor(private val adminAPI: AdminAPI) {

    fun addDevice(device: Device): Single<Device> = adminAPI.addDevice(device)

    fun getDeviceById(id: Long): Single<Device> = adminAPI.getDeviceById(id)

    fun getAllDevices(): Single<List<Device>> = adminAPI.getAllDevices()

    fun updateDevice(id: Long, device: Device): Single<Device> = adminAPI.updateDevice(id, device)

    fun deleteDevice(id: Long): Completable = adminAPI.deleteDevice(id)

    fun addIdsToDevice(deviceId: Long, userIds: List<Long>): Single<Device> =
        adminAPI.addIdsToDevice(deviceId, userIds)

    fun removeIdsFromDevice(deviceId: Long, userIds: List<Long>): Single<Device> =
        adminAPI.removeIdsFromDevice(deviceId, userIds)

    fun getUserDevices(userId: Long): Single<List<Device>> = adminAPI.getUserDevices(userId)

    fun unlinkUserDevices(userIds: Long) = adminAPI.unlinkUserDevices(userIds)
}